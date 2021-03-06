package com.sssarm.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * <a
 * href='https://yq.aliyun.com/articles/594575?spm=a2c4e.11163080.searchblog.9.322c2ec1LVemsm'>整理收集的一些常用java工具类</a>
 * <br>
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/5/31 22:08
 */
public class ResultDto {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static ResultDto build(int status, String msg, Object data) {
        return new ResultDto(status, msg, data);
    }

    public static ResultDto ok(Object data) {
        return new ResultDto(data);
    }

    public static ResultDto ok() {
        return new ResultDto(null);
    }

    public ResultDto() {}

    public static ResultDto build(int status, String msg) {
        return new ResultDto(status, msg, null);
    }

    public ResultDto(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultDto(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public boolean isOK() {
        return this.status == 200;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为TaotaoResult对象
     *
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static ResultDto formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ResultDto.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isObject()) {
                obj = MAPPER.readValue(data.traverse(), clazz);
            } else if (data.isTextual()) {
                obj = MAPPER.readValue(data.asText(), clazz);
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static ResultDto format(String json) {
        try {
            return MAPPER.readValue(json, ResultDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static ResultDto formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj =
                        MAPPER.readValue(
                                data.traverse(),
                                MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
