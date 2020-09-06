package tn.krh.savsamsung.retrofit;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import tn.krh.savsamsung.entity.HelpItem;
import tn.krh.savsamsung.entity.article;
import tn.krh.savsamsung.entity.astuce;
import tn.krh.savsamsung.entity.produit;
import tn.krh.savsamsung.entity.repairD;
import tn.krh.savsamsung.entity.services;
import tn.krh.savsamsung.entity.user;

public interface INodeJS {
    //*********************** post request *********************************
    //ADD NEW REPAIR DEMAND
    @POST("/AddNewRepairDemand/{id}")
    @FormUrlEncoded
    Observable<String> AddNewRepairDemand(@Path("id") int userId,
                                          @Field("type") String type,
                                          @Field("ville") String ville,
                                          @Field("adresse") String adresse,
                                          @Field("generated_num") int generated_num,
                                          @Field("phone") int phone,
                                          @Field("status") String status);
    //*********************register User***********************************
    @POST("/registerUser/")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("fullname") String fullname,
                                    @Field("password") String password,
                                    @Field("adresse") String adresse,
                                    @Field("tel") int tel);
    //*********************login User***********************************
    @POST("/loginUser/")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,@Field("pwd") String password);
    //*********************add commande***********************************
    @POST("/addNewCommand/{id}")
    @FormUrlEncoded
    Observable<String> AddNewCommand(@Path("id") int user_id,
                                      @Field("ref") int ref
                                     ,@Field("produitId") int produit_id
                                     ,@Field("quan") int quan);
    //*********************** get request *********************************
    //get produit by id
    @GET("/getProduitById/{id}")
    Call<produit> getProduitById(@Path("id") int id);
    //get produit by type
    @GET("/GetAllProduitsByType/{type}")
    Call<List<produit>> getAllProduitsByType(@Path("type") String type);
    //get user by email
    @GET("/getAllRepairDbyUser/{id}")
    Call<List<repairD>> getAllRepairDForUserById(@Path("id") int id);
    //get user by email
    @GET("/getUserByEmail/{email}")
    Call<user> getUserDataByEmail(@Path("email") String email);
    //get article by id
    @GET("/getArticleById/{id}")
    Call<article> getArticleById(@Path("id") int id);
    //get all help articles
    @GET("/GetAllHelpModels/")
    Call<List<HelpItem>> getAllHelps();
    //get all  articles reparation
    @GET("/GetAllArticles/")
    Call<List<article>> getAllArticles();
    //get all  astuces  for articles
    @GET("/GetAllAstuces/{articleId}")
    Call<List<astuce>> getAllAstucesByArticleId(@Path("articleId") int id);
    //get all  services
    @GET("/GetAllServices/")
    Call<List<services>> getAllServices();
    //get all  articles by service type
    @GET("/GetAllArticlesByServiceType/{type}")
    Call<List<article>> getAllArticlesByServiceType(@Path("type")  String type);
    //get img for png img
    @GET("/uploads/{upload}")
    Call<ResponseBody> getHelpItemImg(@Path("upload") String n);
    //get img for jpg img
    @GET("/uploadsForJPG/{upload}")
    Call<ResponseBody> getArticleItemImg(@Path("upload") String n);
}
