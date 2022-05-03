package com.ddarahakit.backend.course;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.course.model.*;
import com.ddarahakit.backend.utils.AwsS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ddarahakit.backend.config.BaseResponseStatus.*;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseProvider courseProvider;

    @Autowired
    AwsS3 awsS3;


    public PostCourseRes createCourse(PostCourseReq postCourseReq) throws BaseException {
        //이름 중복
        if (courseProvider.isPreExistsCourse(postCourseReq)) {
            throw new BaseException(POST_COURSES_PRE_EXIST_COURSE);
        }

        try {
            return courseDao.createCourse(postCourseReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public PostCourseRes createCourseWithImage(PostCourseReq postCourseReq, MultipartFile courseimage, MultipartFile coursedetailimage) throws BaseException {
        //이름 중복
        if (courseProvider.isPreExistsCourse(postCourseReq)) {
            throw new BaseException(POST_COURSES_PRE_EXIST_COURSE);
        }

        try {
            PostCourseRes postCourseRes = courseDao.createCourse(postCourseReq);
            String courseimageUrl = awsS3.upload("course", courseimage);
            String coursedetailimageUrl = awsS3.upload("coursedetail", coursedetailimage);
            PostCourseRes postCourseRes2 = courseDao.createCourseImage(courseimageUrl, postCourseRes.getIdx());
            PostCourseRes postCourseRes3 = courseDao.createCourseDetailImage(coursedetailimageUrl, postCourseRes.getIdx());
            return postCourseRes3;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCourseWithAll getCourseWithAll(Integer idx) throws BaseException {
        if (courseDao.isNotExistedCourse(idx)) {
            throw new BaseException(RESPONSE_NULL_ERROR_BY_IDX);
        }

        try {
            GetCourseWithAll getCourseWithAll = courseDao.getCourseWithAll(idx);

            return getCourseWithAll;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetCourseWithAll> getCourseWithAllList() throws BaseException {
        try {
            List<GetCourseWithAll> getCourseWithAllList = courseDao.getCourseWithAllList();

            return getCourseWithAllList;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
