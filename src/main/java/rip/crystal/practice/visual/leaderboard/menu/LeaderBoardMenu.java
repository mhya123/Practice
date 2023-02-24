package rip.crystal.practice.visual.leaderboard.menu;

import com.google.common.collect.Maps;
import rip.crystal.practice.cPractice;
import rip.crystal.practice.game.kit.Kit;
import rip.crystal.practice.visual.leaderboard.menu.button.GlobalStatsButton;
import rip.crystal.practice.visual.leaderboard.menu.button.KitButton;
import rip.crystal.practice.visual.leaderboard.menu.button.PlayerStatsButton;
import rip.crystal.practice.visual.leaderboard.menu.button.StatsButton;
import rip.crystal.practice.utilities.ItemBuilder;
import rip.crystal.practice.utilities.menu.Button;
import rip.crystal.practice.utilities.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class LeaderBoardMenu extends Menu {

    private final Player target;

    @Override
    public String getTitle(Player player) {
        return cPractice.get().getLeaderboardConfig().getString("INVENTORY.TITLE");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player){
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(2, new StatsButton(target));
        buttons.put(4, new PlayerStatsButton());
        buttons.put(6, new GlobalStatsButton());

        AtomicInteger pos = new AtomicInteger();
        Kit.getKits().stream()
                .filter(Kit::isEnabled)
                .filter(kit -> kit.getGameRules().isRanked())
                .filter(kit -> kit.getDisplayIcon() != null)
                .forEach(kit -> {
            pos.getAndIncrement();

            ItemStack PLACEHOLDER_ITEM = new ItemBuilder(Material.valueOf(cPractice.get().getMainConfig().getString("QUEUES.PLACEHOLDER-ITEM-MATERIAL"))).durability(cPractice.get().getMainConfig().getInteger("QUEUES.PLACEHOLDER-ITEM-DATA")).name("&b").build();
            this.fillEmptySlots(buttons, PLACEHOLDER_ITEM);
            buttons.put(kit.getSlot() + 9, new KitButton(kit));
        });

        return buttons;
    }

    @Override
    public int getSize() {
        return cPractice.get().getMainConfig().getInteger("STATS_MENU.SIZE") * 9;
    }
}
