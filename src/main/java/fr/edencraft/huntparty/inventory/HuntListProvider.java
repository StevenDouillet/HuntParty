package fr.edencraft.huntparty.inventory;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import fr.edencraft.huntparty.HuntParty;
import fr.edencraft.huntparty.lang.MessageFR;
import fr.edencraft.huntparty.utils.*;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HuntListProvider implements InventoryProvider {

    private Player player;
    private List<Hunt> hunts;
    private ClickableItem[] items;

    public HuntListProvider(Player player, List<Hunt> hunts) {
        this.player = player;
        this.hunts = hunts;
    }

    public static SmartInventory getInventory(Player player) {
        return SmartInventory.builder()
                .provider(new HuntListProvider(player, HuntParty.hunts))
                .size(5, 9)
                .title(MessageFR.huntListTitleInventoryGui)
                .manager(HuntParty.getInventoryManager())
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        this.items = new ClickableItem[hunts.size()];

        Pagination pagination = contents.pagination();
        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

        contents.set(4, 4, ClickableItem.of(InventoryUtils.createItemStack(Material.BARRIER, 1,ChatColor.DARK_RED + "Fermer"), e -> {
            HuntListProvider.getInventory(player).close(player);
        }));

        contents.set(4, 2, ClickableItem.of(InventoryUtils.createItemStack(
                        Material.ARROW, 1, ChatColor.YELLOW + "● Page précédente ●"),
                e -> HuntListProvider.getInventory(player).open(player, pagination.previous().getPage())));

        contents.set(4, 6, ClickableItem.of(InventoryUtils.createItemStack(
                        Material.ARROW, 1, ChatColor.YELLOW + "● Page suivante ●"),
                e -> HuntListProvider.getInventory(player).open(player, pagination.next().getPage())));

        updateItems();
        System.out.println("JE SUIS LA ");
        System.out.println(items[2].getItem().getItemMeta().getDisplayName());
        System.out.println(items[3].getItem().getItemMeta().getDisplayName());

        contents.pagination().setItems(items);
        contents.pagination().setItemsPerPage(21);
        contents.pagination().addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1)
                .blacklist(0, 1)
                .blacklist(0, 2)
                .blacklist(0, 3)
                .blacklist(0, 4)
                .blacklist(0, 5)
                .blacklist(0, 6)
                .blacklist(0, 7)
                .blacklist(0, 8)

                .blacklist(4, 1)
                .blacklist(4, 2)
                .blacklist(4, 3)
                .blacklist(4, 4)
                .blacklist(4, 5)
                .blacklist(4, 6)
                .blacklist(4, 7)
                .blacklist(4, 8)

                .blacklist(1, 0)
                .blacklist(2, 0)
                .blacklist(3, 0)

                .blacklist(1, 8)
                .blacklist(2, 8)
                .blacklist(3, 8)
        );
    }

    @Override
    public void update(Player player, InventoryContents contents) {}

    private void updateItems() {
        String skullBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiN2NhM2M3ZDMxNGE2MWFiZWQ4ZmMxOGQ3OTdmYzMwYjZlZmM4NDQ1NDI1YzRlMjUwOTk3ZTUyZTZjYiJ9fX0=";
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
        playerProfile.setProperty(new ProfileProperty("textures", skullBase64));
        skullMeta.setPlayerProfile(playerProfile);
        head.setItemMeta(skullMeta);

        for(int i = 0; i < items.length; i++) {
            Hunt hunt = hunts.get(i);
            ArrayList<String> lores = new ArrayList<String>() {{
                add("  " + ChatColor.GRAY + "ID : " + ChatColor.DARK_GRAY + hunt.getId());
                add("  " + ChatColor.GRAY + "Name : " + ChatColor.DARK_GRAY + hunt.getName());
                add("  " + ChatColor.GRAY + "Timed : " + ChatColor.DARK_GRAY + hunt.isTimed());
                add("  " + ChatColor.GRAY + "Time : " + ChatColor.DARK_GRAY + hunt.getTime());
                add("  " + ChatColor.GRAY + "State : " + ChatColor.DARK_GRAY + hunt.getState().getDisplayName());
                add("  " + ChatColor.GRAY + "isBuild : " + ChatColor.DARK_GRAY + hunt.isBuild());
                add("  " + ChatColor.GRAY + "Players : " + ChatColor.DARK_GRAY + hunt.getHuntPlayers().size());
                add("  " + ChatColor.GRAY + "Treasures : " + ChatColor.DARK_GRAY + hunt.getHuntTreasures().size());
            }};
            System.out.println("i: " + i);
            System.out.println("hunt: " + hunt.getName());
            this.items[i] = ClickableItem.of(InventoryUtils.createItemStack(head, 1,ChatColor.GRAY + hunt.getName(), lores), e -> {
                HuntListProvider.getInventory(player).close(player);
            });
            System.out.println("name: " + items[i].getItem().getItemMeta().getDisplayName());
        }
        System.out.println(items[2].getItem().getItemMeta().getDisplayName());
        System.out.println(items[3].getItem().getItemMeta().getDisplayName());
    }
}
