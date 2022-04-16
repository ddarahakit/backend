package com.ddarahakit.backend.course;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.course.model.*;
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
    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<PostCourseRes> createCourseWithImage(@RequestPart PostCourseReq postCourseReq, @RequestPart MultipartFile courseimage, @RequestPart MultipartFile coursedetailimage) {

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

        if (courseimage.getContentType().startsWith("image") == false) {
            return new BaseResponse<>(POST_COURSES_INVALID_IMAGE);
        }

        try {
            PostCourseRes postCourseRes = courseService.createCourseWithImage(postCourseReq, courseimage, coursedetailimage);
            return new BaseResponse<>(postCourseRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{idx}")
    public BaseResponse<GetCourseWithImageRes> getCourseWithImage(@PathVariable Integer idx) {
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
    @GetMapping("")
    public BaseResponse<List<GetCourseWithImageRes>> getCourseWithImageList() {
        try {
            List<GetCourseWithImageRes> getCourseWithImageResList = courseService.getCourseWithImageList();

            return new BaseResponse<>(getCourseWithImageResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}


