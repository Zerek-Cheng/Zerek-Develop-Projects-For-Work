package noppes.npcs.util;

public class JsonException extends Exception
{
    public JsonException(final String message, final NBTJsonUtil.JsonFile json) {
        super(message + ": " + json.getCurrentPos());
    }
}
