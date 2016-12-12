package com.example.luis.app.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 11/12/16.
 */

public class Coupon
{
    String code,type,amount,usage_limit_per_user;

    public Coupon(String code, String type, String amount, String usage_limit_per_user) {
        this.code = code;
        this.type = type;
        this.amount = amount;
        this.usage_limit_per_user = usage_limit_per_user;
    }
    public JSONObject getJson()
    {
        JSONObject object = new JSONObject();
        JSONObject coupon = new JSONObject();
        try {
            coupon.put("code",code);
            coupon.put("type",type);
            coupon.put("amount",amount);
            coupon.put("usage_limit_per_user",usage_limit_per_user);
            object.put("coupon",coupon);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsage_limit_per_user() {
        return usage_limit_per_user;
    }

    public void setUsage_limit_per_user(String usage_limit_per_user) {
        this.usage_limit_per_user = usage_limit_per_user;
    }


}
