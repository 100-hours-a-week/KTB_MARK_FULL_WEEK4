package com.mark.community.controller;

import com.mark.community.dto.*;
import com.mark.community.entity.Comment;
import com.mark.community.entity.Post;
import com.mark.community.entity.User;
import com.mark.community.exception.CustomException;
import com.mark.community.messages.ApiResponseErrorMessage;
import com.mark.community.messages.ApiResponseMessage;
import com.mark.community.response.ApiResponse;
import com.mark.community.service.PostService;
import com.mark.community.utils.IdempotencyUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/temp")
    public ResponseEntity<?> postTemp(@RequestBody PostTempRequest request,
                                                     HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }
        User user = (User) session.getAttribute("user");
        String idempotencyKey = httpRequest.getHeader("Idempotency-Key");
        ResponseEntity<?> idemResponseEntity = IdempotencyUtil.getResponse(idempotencyKey);

        if(idemResponseEntity != null) return idemResponseEntity;

        Post post =  postService.postTemp(request, user);

        ResponseEntity<?> responseEntity = ResponseEntity
                .status(ApiResponseMessage.SUCCESS_POST_TEMP.getStatusCode())
                .header("Location", "/posts/" + post.getPostId())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_POST_TEMP, new PostTempResponse(post.getPostId())));

         IdempotencyUtil.setResponse(idempotencyKey, responseEntity);

        return responseEntity;
    }

    @PatchMapping("/{postId}/temp")
    public ResponseEntity<?> postAutoTemp(@PathVariable String postId,
                                          @RequestPart("request") PostTempRequest request,
                                          @RequestPart("images") MultipartFile[] images,
                                          HttpServletRequest httpRequest
    ){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }
        Post post = postService.postAutoTemp(postId, request, images);

        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_POST_TEMP.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_POST_TEMP,
                        new PostTempResponse(postId, post.getFileIds())));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId, HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }

        User user = (User) session.getAttribute("user");

        postService.deletePost(postId, user.getUserId());
        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_DELETE_POST.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_DELETE_POST));
    }

    @GetMapping("/temp")
    public ResponseEntity<?> getTempPost(@RequestParam String postId, HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }

        Post post = postService.getPost(postId);
        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_GET_POST_TEMP.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_GET_POST_TEMP,
                        new PostTempResponse(postId,post.getTitle(), post.getBody(),post.getFileIds())));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable String postId, HttpServletRequest httpRequest){

        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }

        User user = (User) session.getAttribute("user");

        Post post = postService.getPost(postId);

        boolean isEdited = user.getUserId().equals(post.getUserId());

        Counts counts = new Counts(post.getLikes(), post.getComments(), post.getViews());
        PostResponse postResponse = new PostResponse(
                    post.getPostId(),
                    post.getTitle(),
                    post.getBody(),
                    post.getThumbnailId(),
                    post.getNickname(),
                    post.getUserId(),
                    counts,
                    post.getFileIds(),
                    isEdited
        );


        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_GET_POST.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_GET_POST, postResponse));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> savePost(@PathVariable String postId,
                                      @RequestPart("postRequest") PostRequest postRequest,
                                      @RequestPart("images") MultipartFile[] images,
                                      HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }

        Post post = postService.savePost(postId, postRequest, images);

        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_POST_SAVE.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_POST_SAVE,
                        new PostResponse(post.getPostId())));
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "10") int size,
                                      @RequestParam String lastPostId,
                                      HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession(false);

        if(session == null){
            throw new CustomException(ApiResponseErrorMessage.EXPIRED_SESSION);
        }

        List<Post> posts = postService.getPosts(size, lastPostId);
        List<PostResponse> tempList = new ArrayList<>();

        PostListResponse postListResponse = new PostListResponse();

        for(Post post : posts){
            Counts counts = new Counts(post.getLikes(), post.getComments(), post.getViews());

            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


            PostResponse postResponse =  new PostResponse(
                    post.getPostId(),
                    post.getTitle(),
                    post.getBody(),
                    post.getThumbnailId(),
                    post.getNickname(),
                    post.getUserId(),
                    counts,
                    sd.format(post.getPostTime()),
                    post.isDeleted(),
                    post.isBlind()
            );

            tempList.add(postResponse);
        }

        postListResponse.setTotal(posts.size());
        postListResponse.setList(tempList);




        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_GET_POSTS.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_GET_POSTS, postListResponse));
    }



}
