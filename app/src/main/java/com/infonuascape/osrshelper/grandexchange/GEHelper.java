package com.infonuascape.osrshelper.grandexchange;

import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class GEHelper {
    public GESearchResults search(String itemName, int pageNum) {
		GEFetcher geFetcher = new GEFetcher();

		String output = geFetcher.search(itemName, pageNum);
		//String output = "{\"total\":3020,\"items\":[{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=1319\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=1319\",\"id\":1319,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune 2h sword\",\"description\":\"A two handed sword.\",\"current\":{\"trend\":\"neutral\",\"price\":\"41.3k\"},\"today\":{\"trend\":\"negative\",\"price\":\"- 30\"},\"members\":\"false\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=13024\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=13024\",\"id\":13024,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune armour set (lg)\",\"description\":\"A set containing a full helm, platebody, legs and kiteshield.\",\"current\":{\"trend\":\"neutral\",\"price\":\"144.0k\"},\"today\":{\"trend\":\"negative\",\"price\":\"- 1,278\"},\"members\":\"false\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=13026\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=13026\",\"id\":13026,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune armour set (sk)\",\"description\":\"A set containing a full helm, platebody, skirt and kiteshield.\",\"current\":{\"trend\":\"neutral\",\"price\":\"146.4k\"},\"today\":{\"trend\":\"neutral\",\"price\":0},\"members\":\"false\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=892\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=892\",\"id\":892,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune arrow\",\"description\":\"Arrows with rune heads.\",\"current\":{\"trend\":\"neutral\",\"price\":135},\"today\":{\"trend\":\"negative\",\"price\":\"- 4\"},\"members\":\"true\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=893\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=893\",\"id\":893,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune arrow(p)\",\"description\":\"Venomous-looking arrows.\",\"current\":{\"trend\":\"neutral\",\"price\":347},\"today\":{\"trend\":\"neutral\",\"price\":0},\"members\":\"true\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=5621\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=5621\",\"id\":5621,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune arrow(p+)\",\"description\":\"Venomous-looking arrows.\",\"current\":{\"trend\":\"neutral\",\"price\":434},\"today\":{\"trend\":\"neutral\",\"price\":0},\"members\":\"true\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=5627\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=5627\",\"id\":5627,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune arrow(p++)\",\"description\":\"Venomous-looking arrows.\",\"current\":{\"trend\":\"neutral\",\"price\":933},\"today\":{\"trend\":\"neutral\",\"price\":0},\"members\":\"true\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=44\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=44\",\"id\":44,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune arrowtips\",\"description\":\"I can make some arrows with these.\",\"current\":{\"trend\":\"neutral\",\"price\":307},\"today\":{\"trend\":\"neutral\",\"price\":0},\"members\":\"true\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=1359\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=1359\",\"id\":1359,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune axe\",\"description\":\"A powerful axe.\",\"current\":{\"trend\":\"neutral\",\"price\":\"7,474\"},\"today\":{\"trend\":\"positive\",\"price\":\"+19\"},\"members\":\"false\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=1373\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=1373\",\"id\":1373,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune battleaxe\",\"description\":\"A vicious looking axe.\",\"current\":{\"trend\":\"neutral\",\"price\":\"26.6k\"},\"today\":{\"trend\":\"negative\",\"price\":\"- 606\"},\"members\":\"false\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=4131\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=4131\",\"id\":4131,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune boots\",\"description\":\"These will protect my feet.\",\"current\":{\"trend\":\"neutral\",\"price\":\"8,086\"},\"today\":{\"trend\":\"negative\",\"price\":\"- 374\"},\"members\":\"true\"},{\"icon\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_sprite.gif?id=4803\",\"icon_large\":\"http://services.runescape.com/m=itemdb_oldschool/4936_obj_big.gif?id=4803\",\"id\":4803,\"type\":\"Default\",\"typeIcon\":\"http://www.runescape.com/img/categories/Default\",\"name\":\"Rune brutal\",\"description\":\"Blunt rune arrow...ouch\",\"current\":{\"trend\":\"neutral\",\"price\":206},\"today\":{\"trend\":\"neutral\",\"price\":0},\"members\":\"true\"}]}";


		GESearchResults geSearchResults = new GESearchResults(output);

		return geSearchResults;
    }
}


