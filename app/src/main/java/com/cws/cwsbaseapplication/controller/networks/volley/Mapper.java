package com.cws.cwsbaseapplication.controller.networks.volley;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Singleton wrapper class which configures the Jackson JSON parser.
 */
public final class Mapper
{
    private static ObjectMapper MAPPER;

    public static ObjectMapper get()
    {
        if (MAPPER == null)
        {
            MAPPER = new ObjectMapper();

            // This is useful for me in case I add new object properties on the server side which are not yet available on the client.   
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        return MAPPER;
    }

    public static String string(Object data)
    {
        try
        {
            return get().writeValueAsString(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T objectOrThrow(String data, Class<T> type) throws JsonParseException, JsonMappingException, IOException
    {
        return get().readValue(data, type);
    }

    public static <T> JacksonRequest.HttpResponse read(String jsonString, Class<T> context) throws IOException {
        JavaType type = get().getTypeFactory().constructParametrizedType(JacksonRequest.HttpResponse.class, JacksonRequest.HttpResponse.class, context);
        return get().readValue(jsonString, type);
    }

    public static <T> T objectOrThrow(String data, Class collectionClazz, Class<T> ClazzType)
    {
        try
        {
            JavaType type = MAPPER.getTypeFactory().
                    constructCollectionType(collectionClazz, ClazzType);
            return MAPPER.readValue(data, type);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}