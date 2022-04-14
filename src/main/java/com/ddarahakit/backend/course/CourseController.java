package com.ddarahakit.backend.course;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.course.model.GetCourseRes;
import com.ddarahakit.backend.course.model.GetCourseWithImageRes;
import com.ddarahakit.backend.course.model.PostCourseReq;
import com.ddarahakit.backend.course.model.PostCourseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ddarahakit.backend.config.BaseResponseStatus.*;
import static com.ddarahakit.backend.utils.Validation.*;


@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseProvider courseProvider;


    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<PostCourseRes> createCourse(@RequestBody PostCourseReq postCourseReq) {
        if (postCourseReq.getName() == null || postCourseReq.getName().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_NAME);
        }
        if (postCourseReq.getPrice() == null || postCourseReq.getPrice().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_PRICE);
        }
        if (postCourseReq.getDescription() == null || postCourseReq.getDescription().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_DESCRIPTION);
        }
        if (postCourseReq.getDiscount() == null || postCourseReq.getDiscount().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_DISCOUNT);
        }
        if (postCourseReq.getCategory_idx() == null || postCourseReq.getCategory_idx().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_CATEGORY_IDX);
        }

        try {
            PostCourseRes postCourseRes = courseService.createCourse(postCourseReq);
            return new BaseResponse<>(postCourseRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{idx}")
    public BaseResponse<GetCourseRes> getProduct(@PathVariable Integer idx) {
        if (idx == null) {
            return new BaseResponse<>(GET_COURSES_EMPTY_IDX);
        }
        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(GET_COURSES_INVALID_IDX);
        }

        try {
            GetCourseRes getCourseRes = courseService.getCourse(idx);

            return new BaseResponse<>(getCourseRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetCourseRes>> getProductList() {
        try {
            List<GetCourseRes> getCourseResList = courseService.getCourseList();

            return new BaseResponse<>(getCourseResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<PostCourseRes> createCourseWithImage(@RequestPart PostCourseReq postCourseReq, @RequestPart MultipartFile multipartFile) {

        if (postCourseReq.getName() == null || postCourseReq.getName().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_NAME);
        }
        if (postCourseReq.getPrice() == null || postCourseReq.getPrice().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_PRICE);
        }
        if (postCourseReq.getDescription() == null || postCourseReq.getDescription().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_DESCRIPTION);
        }
        if (postCourseReq.getDiscount() == null || postCourseReq.getDiscount().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_DISCOUNT);
        }
        if (postCourseReq.getCategory_idx() == null || postCourseReq.getCategory_idx().equals("")) {
            return new BaseResponse<>(POST_COURSES_EMPTY_CATEGORY_IDX);
        }

        if (multipartFile.getContentType().startsWith("image") == false) {
            return new BaseResponse<>(POST_COURSES_INVALID_IMAGE);
        }

        try {
            PostCourseRes postCourseRes = courseService.createCourseWithImage(postCourseReq, multipartFile);
            return new BaseResponse<>(postCourseRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @ResponseBody
    @GetMapping("/with/{idx}")
    public BaseResponse<GetCourseWithImageRes> getProductWithImage(@PathVariable Integer idx) {
        if (idx == null) {
            return new BaseResponse<>(GET_COURSES_EMPTY_IDX);
        }
        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(GET_COURSES_INVALID_IDX);
        }

        try {
            GetCourseWithImageRes getCourseWithImageRes = courseService.getCourseWithImage(idx);

            return new BaseResponse<>(getCourseWithImageRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/with")
    public BaseResponse<List<GetCourseWithImageRes>> getProductWithImageList() {
        try {
            List<GetCourseWithImageRes> getCourseWithImageResList = courseService.getCourseWithImageList();

            return new BaseResponse<>(getCourseWithImageResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}


