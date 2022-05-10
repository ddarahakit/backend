package com.ddarahakit.backend.course.lesson;


import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import com.ddarahakit.backend.course.chapter.ChapterService;
import com.ddarahakit.backend.course.chapter.model.PostChapterReq;
import com.ddarahakit.backend.course.chapter.model.PostChapterRes;
import com.ddarahakit.backend.course.lesson.model.GetLessonReq;
import com.ddarahakit.backend.course.lesson.model.GetLessonRes;
import com.ddarahakit.backend.course.lesson.model.PostLessonReq;
import com.ddarahakit.backend.course.lesson.model.PostLessonRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<PostLessonRes> createLesson(@RequestBody PostLessonReq postLessonReq) {
        try {
            PostLessonRes postLessonRes = lessonService.createLesson(postLessonReq);
            return new BaseResponse<>(postLessonRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{idx}")
    public BaseResponse<GetLessonRes> getLessonByIdx(@PathVariable Integer idx) {
        try {
            GetLessonRes getLessonRes = lessonService.getLessonByIdx(idx);
            return new BaseResponse<>(getLessonRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}


