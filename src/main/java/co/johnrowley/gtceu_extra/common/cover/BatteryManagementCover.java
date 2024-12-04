package co.johnrowley.gtceu_extra.common.cover;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.cover.IUICover;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.IntInputWidget;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.ElectricStats;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.MachineCoverContainer;
import com.gregtechceu.gtceu.common.block.BatteryBlock;
import com.gregtechceu.gtceu.common.cover.StorageCover;

import com.gregtechceu.gtceu.common.machine.electric.BatteryBufferMachine;
import com.gregtechceu.gtceu.utils.GTTransferUtils;
import com.llamalad7.mixinextras.sugar.Local;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SwitchWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import net.dries007.tfc.common.capabilities.InventoryItemHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import lombok.Getter;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

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

    public BatteryManagementCover(@NotNull CoverDefinition definition, @NotNull ICoverable coverableView,
                                  @NotNull Direction attachedSide, int tier) {
        super(definition, coverableView, attachedSide);

        this.subscriptionHandler = new ConditionalSubscriptionHandler(coverHolder, this::update, this::isSubscriptionActive);
        this.io = IO.OUT;
        this.tier = tier;
    }

    protected @Nullable IItemHandlerModifiable getOwnItemHandler() {
        return coverHolder.getItemHandlerCap(attachedSide, false);
    }

    protected boolean isSubscriptionActive() {
        return isWorkingEnabled() && getAdjacentItemHandler() != null;
    }

    protected @Nullable IItemHandler getAdjacentItemHandler() {
        return GTTransferUtils.getAdjacentItemHandler(coverHolder.getLevel(), coverHolder.getPos(), attachedSide)
                .resolve().orElse(null);
    }

    private int getExportThreshold() {
        return this.exportWhenFull ? 1 : 0;
    }

    private void setExportThreshold(int value) {
        this.exportWhenFull = value > 0;
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

    /// ///////////////////////////////////

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

    private static long getCharge(ItemStack itemStack, ElectricStats electricStats) {
        var tagCompound = itemStack.getTag();
        if (tagCompound == null)
            return 0;
        return Math.min(tagCompound.getLong("Charge"), electricStats.maxCharge);
    }

    private static Optional<ElectricStats> getElectricStats(ComponentItem component) {
        return component
            .getComponents()
            .stream()
            .filter(c -> (c instanceof ElectricStats))
            .map(c -> (ElectricStats)c)
            .findFirst();
    }

    private int getAppropriateBatterySlotFromAdjacentItemHandler(IItemHandler adjacent) {
        for (var i = 0; i < adjacent.getSlots(); i++) {
            var itemStack = adjacent.getStackInSlot(i);

            var item = itemStack.getItem();
            if (!(item instanceof ComponentItem component)) {
                continue;
            }

            var optionalStats = getElectricStats(component);
            if (optionalStats.isEmpty()) {
                continue;
            }

            var stats = optionalStats.get();
            if (stats.tier != this.tier) {
                continue;
            }

            return i;
        }

        return -1;
    }

    private void tryImportBatteries(BatteryBufferMachine machine, IItemHandler adjacent) {
        var batteryInventory = machine.getBatteryInventory();
        var slots = batteryInventory.getSlots();

        // when there are no batteries available for our tier, we bail.
        var appropriateBatteryIndex = getAppropriateBatterySlotFromAdjacentItemHandler(adjacent);
        if (appropriateBatteryIndex == -1) {
            return;
        }

        var itemStack = adjacent.getStackInSlot(appropriateBatteryIndex);

        ItemStack remainder = ItemHandlerHelper.insertItem(batteryInventory, itemStack, true);
        if (remainder.isEmpty() || remainder.getCount() < itemStack.getCount()) {
            ItemHandlerHelper.insertItem(batteryInventory, itemStack, false);
            ItemHandlerHelper.insertItem(machine.getItemHandlerCap(Direction.DOWN, false), itemStack, false);
            adjacent.extractItem(appropriateBatteryIndex, 1, false);
        }
    }

    private boolean shouldExportBattery(long charge, long maxCharge) {
        if (this.exportWhenFull) {
            return charge == maxCharge;
        }

        return charge == 0;
    }

    private void tryExportBatteries(BatteryBufferMachine machine, IItemHandler adjacent){
        var slots = machine.getBatteryInventory().getSlots();
        var batteryInventory = machine.getBatteryInventory();
        for (var i = 0; i < slots; i++) {
            var itemStack = batteryInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) {
                continue;
            }

            var item = itemStack.getItem();
            if (!(item instanceof ComponentItem component)) {
                continue;
            }

            var optionalStats = getElectricStats(component);
            if (optionalStats.isEmpty()) {
                continue;
            }
            var stats = optionalStats.get();

            var charge = getCharge(itemStack, stats);
            var maxCharge = stats.maxCharge;

            if (this.shouldExportBattery(charge, maxCharge)) {
                ItemStack remainder = ItemHandlerHelper.insertItem(adjacent, itemStack, true);
                if (remainder.isEmpty() || remainder.getCount() < itemStack.getCount()) {
                    ItemHandlerHelper.insertItem(adjacent, itemStack, false);
                    batteryInventory.extractItem(i, 1, false);
                }

                return;
            }
        }
    }

    protected void update() {
        long timer = coverHolder.getOffsetTimer();

        if (timer % 20 == 0) {
            var machine = getBatteryBufferMachine();
            if (machine == null) {
                return;
            }

            var adjacent = getAdjacentItemHandler();
            if (adjacent == null) {
                return;
            }


            switch (io) {
                case IN -> tryImportBatteries(machine, adjacent);
                case OUT -> tryExportBatteries(machine, adjacent);
            }

//            if (itemsLeftToTransferLastSecond > 0) {
//                var adjacent = getAdjacentItemHandler();
//                var self = getOwnItemHandler();
//
//                if (adjacent != null && self != null) {
//                    int totalTransferred = switch (io) {
//                        case IN -> doTransferItems(adjacent, self, itemsLeftToTransferLastSecond);
//                        case OUT -> doTransferItems(self, adjacent, itemsLeftToTransferLastSecond);
//                        default -> 0;
//                    };
//                    this.itemsLeftToTransferLastSecond -= totalTransferred;
//                }
//            }
//            if (timer % 20 == 0) {
//                this.itemsLeftToTransferLastSecond = transferRate;
//            }
            subscriptionHandler.updateSubscription();
        }
    }

    //////////////////////////////////////
    // *********** GUI ***********//
    //////////////////////////////////////
    @Override
    public Widget createUIWidget() {
        final var group = new WidgetGroup(0, 0, 176, 100);
        group.addWidget(new LabelWidget(10, 5, Component.translatable(getUITitle(), GTValues.VN[tier]).getString()));

        group.addWidget(new IntInputWidget(10, 20, 156, 20, this::getExportThreshold, this::setExportThreshold)
                .setMin(0).setMax(1))
                .setHoverTooltips(
                        LocalizationUtils.format("cover.batterymanagementcover.threshold")
                );

        ioModeSwitch = new SwitchWidget(10, 45, 20, 20,
                (clickData, value) -> {
                    setIo(value ? IO.IN : IO.OUT);
                    ioModeSwitch.setHoverTooltips(
                            LocalizationUtils.format("cover.batterymanagementcover.mode", LocalizationUtils.format(io.tooltip)));
                })
                .setTexture(
                        new GuiTextureGroup(GuiTextures.VANILLA_BUTTON, IO.OUT.icon),
                        new GuiTextureGroup(GuiTextures.VANILLA_BUTTON, IO.IN.icon))
                .setPressed(io == IO.IN)
                .setHoverTooltips(
                        LocalizationUtils.format("cover.batterymanagementcover.mode", LocalizationUtils.format(io.tooltip)));

        group.addWidget(ioModeSwitch);

        return group;
    }


    @NotNull
    protected String getUITitle() {
        return "cover.batterymanagementcover.title";
    }
}