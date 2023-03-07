package akhid.development.controller;

import akhid.development.service.otp.OtpService;

import javax.inject.Inject;

import io.vertx.core.json.JsonObject;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/otp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OtpController {
    @Inject
    OtpService service;

    @GET
    public JsonObject getOtp(@QueryParam String session_key) {

        // Jika session key tidak ditemukan, dikembalikan response error
        if (!service.keyExists(session_key)) {
            return errorResponse();
        }
        JsonObject result = new JsonObject();
        result.put("OTP: ", service.getOtp(session_key));
        result.put("TTL: ", service.getOtpTTL(session_key));
        return result;
    }

    @POST
    public JsonObject newOtp(@QueryParam String session_key) {
        service.newOtp(session_key);

        JsonObject result = new JsonObject();
        result.put("Message: ", "The session key successfully send.");
        return result;
    }

    JsonObject errorResponse() {
        JsonObject result = new JsonObject();
        result.put("Message: ", "The OTP key doesn't exist.");
        return result;
    }
}
