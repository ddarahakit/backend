package com.ddarahakit.backend.course;

import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.course.model.GetCourseRes;
import com.ddarahakit.backend.course.model.GetCourseWithImageRes;
import com.ddarahakit.backend.course.model.PostCourseReq;
import com.ddarahakit.backend.course.model.PostCourseRes;
import com.ddarahakit.backend.utils.AwsS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    public GetCourseRes getCourse(Integer idx) throws BaseException {
        if (courseDao.isNotExistedCourse(idx)) {
            throw new BaseException(RESPONSE_NULL_ERROR_BY_IDX);
        }

        try {
            GetCourseRes getCourseRes = courseDao.getCourse(idx);

            return getCourseRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCourseRes> getCourseList() throws BaseException {
        try {
            List<GetCourseRes> getCourseResList = courseDao.getCourseList();

            return getCourseResList;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public PostCourseRes createCourseWithImage(PostCourseReq postCourseReq, MultipartFile multipartFile) throws BaseException {
        //이름 중복
        if (courseProvider.isPreExistsCourse(postCourseReq)) {
            throw new BaseException(POST_COURSES_PRE_EXIST_COURSE);
        }

        try {
            PostCourseRes postCourseRes = courseDao.createCourse(postCourseReq);
            String imageUrl = awsS3.upload(multipartFile);
            PostCourseRes postCourseRes2 = courseDao.createCourseImage(imageUrl, postCourseRes.getIdx());
            return postCourseRes2;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }


    }

    public GetCourseWithImageRes getCourseWithImage(Integer idx) throws BaseException {
        if (courseDao.isNotExistedCourse(idx)) {
            throw new BaseException(RESPONSE_NULL_ERROR_BY_IDX);
        }

        try {
            GetCourseWithImageRes getCourseWithImageRes = courseDao.getCourseWithImage(idx);

            return getCourseWithImageRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetCourseWithImageRes> getCourseWithImageList() throws BaseException {
        try {
            List<GetCourseWithImageRes> getCourseWithImageResList = courseDao.getCourseWithImageList();

            return getCourseWithImageResList;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
