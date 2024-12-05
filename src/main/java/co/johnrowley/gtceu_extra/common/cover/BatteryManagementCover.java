package co.johnrowley.gtceu_extra.common.cover;

import co.johnrowley.gtceu_extra.common.utils.BackportUtils;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.cover.IUICover;
import com.gregtechceu.gtceu.api.gui.GuiTextures;

import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.MachineCoverContainer;
import com.gregtechceu.gtceu.common.machine.electric.BatteryBufferMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SwitchWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.side.item.IItemTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import lombok.Getter;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.function.Predicate;

import static co.johnrowley.gtceu_extra.api.gui.GuiTextures.BATTERY_EMPTY_OVERLAY;
import static co.johnrowley.gtceu_extra.api.gui.GuiTextures.BATTERY_FULL_OVERLAY;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BatteryManagementCover extends CoverBehavior implements IUICover, IControllable {
    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(BatteryManagementCover.class,
            CoverBehavior.MANAGED_FIELD_HOLDER);
    protected final ConditionalSubscriptionHandler subscriptionHandler;
    @Persisted
    @DescSynced
    @Getter
    @RequireRerender
    /**
     * io indicates the direction of the cover. If it's OUT then the cover will export a battery depending on the
     * exportWhenFull boolean.
     */
    protected IO io;

    @Persisted
    @DescSynced
    @Getter
    /**
     * exportWhenFull indicates if the battery should be exported when full, or when empty. This is useful because it
     * allows some battery buffers to charge batteries, and some to drain batteries.
     */
    private boolean exportWhenFull;

    @Persisted
    @Getter
    protected boolean isWorkingEnabled = true;
    public final int tier;
    private Widget ioModeSwitch;
    private Widget thresholdSwitch;

    public BatteryManagementCover(@NotNull CoverDefinition definition, @NotNull ICoverable coverableView,
                                  @NotNull Direction attachedSide, int tier) {
        super(definition, coverableView, attachedSide);

        this.subscriptionHandler = new ConditionalSubscriptionHandler(coverHolder, this::update, this::isSubscriptionActive);
        this.io = IO.OUT;
        this.tier = tier;
        this.exportWhenFull = true;
    }

    protected boolean isSubscriptionActive() {
        return isWorkingEnabled() && getAdjacentItemHandler() != null;
    }

    protected @Nullable IItemHandler getAdjacentItemHandler() {
        return BackportUtils.getAdjacentItemHandler(coverHolder.getLevel(), coverHolder.getPos(), attachedSide)
                .resolve().orElse(null);
    }

    private void setExportThreshold(boolean full) {
        this.exportWhenFull = full;
    }

    //////////////////////////////////////
    // ***** Initialization ******//
    //////////////////////////////////////

    @Override
    public boolean canAttach() {
        var machine = getBatteryBufferMachine();
        if (machine == null) {
            return false;
        }

        return super.canAttach();
    }

    @Nullable
    private BatteryBufferMachine getBatteryBufferMachine() {
        if (!(coverHolder instanceof MachineCoverContainer container)) {
            return null;
        }

        var blockEntity = coverHolder.getLevel().getBlockEntity(coverHolder.getPos());
        if (blockEntity == null) {
            return null;
        }

        if (!(blockEntity instanceof MetaMachineBlockEntity metaEntity)) {
            return null;
        }

        if (!(metaEntity.metaMachine instanceof BatteryBufferMachine batteryBufferMachine)) {
            return null;
        }

        return batteryBufferMachine;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        subscriptionHandler.initialize(coverHolder.getLevel());
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        subscriptionHandler.unsubscribe();
    }

    public void setIo(IO io) {
        if (io == IO.IN || io == IO.OUT) {
            this.io = io;
        }
        subscriptionHandler.updateSubscription();
        coverHolder.markDirty();
    }

    //////////////////////////////////////
    // ***** Transfer Logic *****//
    //////////////////////////////////////

    @Override
    public void onNeighborChanged(Block block, BlockPos fromPos, boolean isMoving) {
        subscriptionHandler.updateSubscription();
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        if (this.isWorkingEnabled != isWorkingAllowed) {
            this.isWorkingEnabled = isWorkingAllowed;
            subscriptionHandler.updateSubscription();
        }
    }

    private Optional<Pair<Integer, ItemStack>> getAppropriateBatteryFromAdjacentItemHandler(Predicate<ItemStack> predicate, IItemHandler adjacent) {
        for (var i = 0; i < adjacent.getSlots(); i++) {
            var itemStack = adjacent.getStackInSlot(i);
            if (itemStack.isEmpty()) {
                continue;
            }

            var electricItem = GTCapabilityHelper.getElectricItem(itemStack);
            if (electricItem == null) {
                continue;
            }

            if (this.tier != electricItem.getTier()) {
                continue;
            }

            if (!predicate.test(itemStack)){
                continue;
            }

            return Optional.of(Pair.of(i, itemStack));
        }

        return Optional.empty();
    }

    private Optional<Integer> getBatteryBufferOpenSlot(ItemStackTransfer batteryInventory) {
        for (var i = 0; i < batteryInventory.getSlots(); i++) {
            var slot = batteryInventory.getStackInSlot(i);
            if (slot.isEmpty()) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    private void tryImportBatteries(BatteryBufferMachine machine, IItemHandler adjacent) {
        var batteryInventory = machine.getBatteryInventory();

        // when there are no batteries available for our tier, we bail.
        var adjacentBattery = getAppropriateBatteryFromAdjacentItemHandler((itemStack -> {
            if (itemStack.isEmpty()) {
                return false;
            }

            var electricItem = GTCapabilityHelper.getElectricItem(itemStack);
            if (electricItem == null) {
                return false;
            }

            return electricItem.getTier() == this.tier;
        }), adjacent);
        if (adjacentBattery.isEmpty()) {
            return;
        }

        var destinationSlot = getBatteryBufferOpenSlot(batteryInventory);
        if (destinationSlot.isEmpty()) {
            return;
        }

        adjacent.extractItem(adjacentBattery.get().getLeft(), 1, false);
        batteryInventory.insertItem(
                destinationSlot.get(),
            adjacentBattery.get().getRight(),
            false
        );
        batteryInventory.onContentsChanged(destinationSlot.get());
    }

    /**
     * batteryBufferFilter is used to override a battery buffer's built in filter. This is to help prevent the battery
     * buffer from "eating" batteries.
     * @param itemStack an item that may be a battery
     * @return true if the item is in fact a battery.
     */
    private static boolean batteryBufferFilter(ItemStack itemStack){
        if (GTCapabilityHelper.getElectricItem(itemStack) != null) {
            return true;
        }

        if (ConfigHolder.INSTANCE.compat.energy.nativeEUToPlatformNative && GTCapabilityHelper.getForgeEnergyItem(itemStack) != null) {
            return true;
        }

        return false;
    }

    private boolean shouldExportBattery(IElectricItem item) {
        if (this.exportWhenFull) {
            return item.getCharge() == item.getMaxCharge();
        }

        // here we do a consider a battery that is 1% full as empty because batteries can have enough of a charge to not
        // be able to send out a packet, and thus get stuck.
        return item.getMaxCharge() / 100 > item.getCharge();
    }

    private Optional<Pair<Integer,ItemStack>> getExportableBatterySlot(IItemTransfer batteryInventory) {
        var slots = batteryInventory.getSlots();

        for (var i = 0; i < slots; i++) {
            var itemStack = batteryInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) {
                continue;
            }

            var electricItem = GTCapabilityHelper.getElectricItem(itemStack);
            if (electricItem == null) {
                continue;
            }

            if (this.shouldExportBattery(electricItem)) {
                return Optional.of(Pair.of(i, itemStack));
            }
        }

        return Optional.empty();
    }

    private void tryExportBatteries(BatteryBufferMachine machine, IItemHandler adjacent){
        var batteryInventory = machine.getBatteryInventory();
        var slot = getExportableBatterySlot(batteryInventory);
        if (slot.isEmpty()) {
            return;
        }

        var exportableBattery = slot.get().getRight();

        ItemStack remainder = ItemHandlerHelper.insertItem(adjacent, exportableBattery, true);
        if (remainder.isEmpty() || remainder.getCount() < exportableBattery.getCount()) {
            ItemHandlerHelper.insertItem(adjacent, exportableBattery, false);
            batteryInventory.extractItem(slot.get().getLeft(), 1, false);
        }
    }

    protected void update() {
        long timer = coverHolder.getOffsetTimer();

        if (timer % 20 != 0) {
            return;
        }

        var machine = getBatteryBufferMachine();
        if (machine == null) {
            return;
        }

        machine
            .getBatteryInventory()
            .setFilter(BatteryManagementCover::batteryBufferFilter);

        var adjacent = getAdjacentItemHandler();
        if (adjacent == null) {
            return;
        }

        switch (io) {
            case IN -> tryImportBatteries(machine, adjacent);
            case OUT -> tryExportBatteries(machine, adjacent);
        }

        subscriptionHandler.updateSubscription();
    }

    //////////////////////////////////////
    // *********** GUI ***********//
    //////////////////////////////////////
    @Override
    public Widget createUIWidget() {
        final var group = new WidgetGroup(0, 0, 100, 100);
        group.addWidget(new LabelWidget(10, 5, Component.translatable(getUITitle(), GTValues.VN[tier]).getString()));

        thresholdSwitch = new SwitchWidget(10, 20, 20, 20,
                ((clickData, value) -> {
                    setExportThreshold(!value);
                    thresholdSwitch.setHoverTooltips(
                            LocalizationUtils.format("cover.battery_management_cover.threshold.%s", this.exportWhenFull ? "full": "empty")
                    );
                }))
                .setTexture(
                    new GuiTextureGroup(GuiTextures.VANILLA_BUTTON, BATTERY_FULL_OVERLAY),
                    new GuiTextureGroup(GuiTextures.VANILLA_BUTTON, BATTERY_EMPTY_OVERLAY))
                .setPressed(this.exportWhenFull)
                .setHoverTooltips(
                        LocalizationUtils.format("cover.battery_management_cover.threshold.%s", this.exportWhenFull ? "full": "empty")
                );

        ioModeSwitch = new SwitchWidget(10, 45, 20, 20,
                (clickData, value) -> {
                    setIo(value ? IO.IN : IO.OUT);
                    ioModeSwitch.setHoverTooltips(
                            LocalizationUtils.format("cover.battery_management_cover.mode.%s", LocalizationUtils.format(io.tooltip.toLowerCase())));
                })
                .setTexture(
                        new GuiTextureGroup(GuiTextures.VANILLA_BUTTON, IO.OUT.icon),
                        new GuiTextureGroup(GuiTextures.VANILLA_BUTTON, IO.IN.icon))
                .setPressed(io == IO.IN)
                .setHoverTooltips(
                        LocalizationUtils.format("cover.battery_management_cover.mode.%s", LocalizationUtils.format(io.tooltip.toLowerCase())));

        group.addWidget(thresholdSwitch);
        group.addWidget(ioModeSwitch);

        return group;
    }

    @NotNull
    protected String getUITitle() {
        return "cover.battery_management_cover.title";
    }
}