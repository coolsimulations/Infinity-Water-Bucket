package net.coolsimulations.InfinityWaterBucket.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.coolsimulations.InfinityWaterBucket.IWBReference;
import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IWBConfig {

    static File file;

    protected static boolean ilb;
    protected static boolean imb;
    protected static boolean isb;

    public static void init(File fileSrc) {
        if(!fileSrc.exists() || fileSrc.length() <= 2)
            save(fileSrc, setValues());
        else
            load(fileSrc);

        file = fileSrc;
    }

    public static void save(File fileSrc, JsonObject object) {
        try {
            FileWriter file = new FileWriter(fileSrc);
            file.write(new GsonBuilder().setPrettyPrinting().create().toJson(object));
            file.close();
        } catch (IOException e) {
            IWBReference.LOG.error("Couldn't save config");
        }
    }

    public static void load(File fileSrc) {
        try {
            Object obj = JsonParser.parseReader(new FileReader(fileSrc));
            JsonObject jsonObjectRead = (JsonObject) obj;

            ilb = jsonObjectRead.get(InfinityWaterBucketCommon.CONFIG.LAVA_BUCKET).getAsBoolean();
            imb = jsonObjectRead.get(InfinityWaterBucketCommon.CONFIG.MILK_BUCKET).getAsBoolean();
            isb = jsonObjectRead.get(InfinityWaterBucketCommon.CONFIG.SOLID_BUCKET).getAsBoolean();

        } catch (Exception e2) {
            IWBReference.LOG.error("Couldn't load config");
        }
    }

    public static File getFile() {
        return file;
    }

    public static JsonObject setValues() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(InfinityWaterBucketCommon.CONFIG.LAVA_BUCKET, ilb);
        jsonObject.addProperty(InfinityWaterBucketCommon.CONFIG.MILK_BUCKET, imb);
        jsonObject.addProperty(InfinityWaterBucketCommon.CONFIG.SOLID_BUCKET, isb);

        return jsonObject;
    }
}
