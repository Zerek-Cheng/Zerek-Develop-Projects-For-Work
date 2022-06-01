package com._0myun.systemtransaction.systemtransaction.util;

import lombok.Data;

public class Minecraft {
    public static IdParser parseItemId(String id) {
        String[] ids = id.replace("，", ",").split(":");
        IdParser idParser = new IdParser();
        idParser.setId(Integer.parseInt(ids[0]));
        if (ids.length >= 2) {
            if (ids[1].equalsIgnoreCase("*"))
                ids[1] = "-1";
            idParser.setId(Integer.valueOf(ids[0]));
            idParser.setSubId(Short.parseShort(ids[1]));
        }
        return idParser;
    }
    @Data
    public static class IdParser {
        private int id;
        private short subId;
    }
}

