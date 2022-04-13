package com.ddarahakit.backend.course;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.course.model.GetCourseRes;
import com.ddarahakit.backend.course.model.PostCourseReq;
import com.ddarahakit.backend.course.model.PostCourseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_EMPTY_NAME;
import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_EMPTY_PRICE;
import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_EMPTY_DESCRIPTION;
import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_EMPTY_DETAIL;
import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_EMPTY_DISCOUNT;
import static com.ddarahakit.backend.config.BaseResponseStatus.POST_COURSES_EMPTY_CATEGORY_IDX;
import static com.ddarahakit.backend.config.BaseResponseStatus.GET_COURSES_EMPTY_IDX;
import static com.ddarahakit.backend.config.BaseResponseStatus.GET_COURSES_INVALID_IDX;

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
        if (postCourseReq.getName() == null) {
            return new BaseResponse<>(POST_COURSES_EMPTY_NAME);
        }
        if (postCourseReq.getPrice() == null) {
            return new BaseResponse<>(POST_COURSES_EMPTY_PRICE);
        }
        if (postCourseReq.getDescription() == null) {
            return new BaseResponse<>(POST_COURSES_EMPTY_DESCRIPTION);
        }
        if (postCourseReq.getDetail() == null) {
            return new BaseResponse<>(POST_COURSES_EMPTY_DETAIL);
        }
        if (postCourseReq.getDiscount() == null) {
            return new BaseResponse<>(POST_COURSES_EMPTY_DISCOUNT);
        }
        if (postCourseReq.getCategory_idx() == null) {
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
            GetCourseRes getCourseRes = courseProvider.getCourse(idx);

            return new BaseResponse<>(getCourseRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}


