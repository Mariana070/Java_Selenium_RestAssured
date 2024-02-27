package utils;

public class PayloadUtils {
    public static String getPetPayload(int petId, String petName) {
        String petPayload = "{\n" +
                "  \"id\":" + petId + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"Mariana\"\n" +
                "  },\n" +
                "  \"name\": \"" + petName + "\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://s3.amazon.com\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    \n" +
                "  ],\n" +
                "  \"status\": \"sdet doggie\"\n" +
                "}";
        return petPayload;
    }
}
