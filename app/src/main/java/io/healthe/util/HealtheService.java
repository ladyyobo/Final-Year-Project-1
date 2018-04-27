package io.healthe.util;

import org.json.JSONObject;

import io.healthe.model.ImageResponse;
import io.healthe.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Models the Healthe API
 */
public interface HealtheService {
	
	@FormUrlEncoded
	@POST("login_user.php")
	Call<User> loginUser(@Field("email") String email, @Field("password") String password);
	
	//Creates new user
	@FormUrlEncoded
	@POST("create_user.php")
	Call<User> createUser(
			@Field("name") String name,
			@Field("dob") String dob,
			@Field("weight") String weight,
			@Field("height") String height,
			@Field("email") String email,
			@Field("user_password") String password
	);

	//Get product details from the database
	@FormUrlEncoded
	@POST("get_product_details.php")
	Call<ImageResponse> getProductDetails(
			@Field("user_id") String userID,
			@Field("product_id") String productID
	);
}
