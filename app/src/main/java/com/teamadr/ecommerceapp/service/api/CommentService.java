package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.comment.NewComment;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.comment.CommentDto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentService {
    @GET("/api/comment/comments")
    Observable<ResponseBody<Page<CommentDto>>> getPageComments(
            @Query("productId") Integer productId,
            @Query(RequestContansts.SORT_BY) List<String> sortBy,
            @Query(RequestContansts.SORT_TYPE) List<String> sortType,
            @Query(RequestContansts.PAGE_INDEX) int pageIndex,
            @Query(RequestContansts.PAGE_SIZE) int pageSize);

    @POST("/api/comment/new-comment")
    Observable<ResponseBody> createNewComment(@Header(RequestContansts.AUTHORIZATION) String token,
                                              @Body NewComment newComment);

    @HTTP(method = "DELETE", path = "/api/comment/comments/{id}", hasBody = true)
    Observable<ResponseBody> deleteComment(@Header(RequestContansts.AUTHORIZATION) String token,
                                           @Path("id") String commentId);
}
