package com.example.demo.service.post;

import com.example.demo.model.dto.PostDTO;
import com.example.demo.model.dto.PostResponseDto;
import com.example.demo.model.entity.Post;
import com.example.demo.repository.IPostRepository;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.image.IImageService;
import com.example.demo.service.likeComment.ILikeCommentService;
import com.example.demo.service.likePost.ILikePostService;
import com.example.demo.service.reply.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ILikePostService likePostService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IReplyService replyService;

    @Autowired
    private ILikeCommentService likeCommentService;
    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return this.postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public Post save(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public void remove(Long id) {
        this.postRepository.deleteById(id);
    }


    @Override
    public String getDiffDays(Date time1, Date time2) {
        long timeDifferenceMilliseconds = (time2.getTime() - time1.getTime());
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365);

        if (diffSeconds < 1) {
            return "vừa xong";
        } else if (diffMinutes < 1) {
            return diffSeconds + " giây";
        } else if (diffHours < 1) {
            return diffMinutes + " phút";
        } else if (diffDays < 1) {
            return diffHours + " giờ";
        } else if (diffWeeks < 1) {
            return diffDays + " ngày";
        } else if (diffMonths < 1) {
            return diffWeeks + " tuần";
        } else if (diffYears < 1) {
            return diffMonths + " tháng";
        } else {
            return diffYears + " năm";
        }
    }

    @Override
    public PostDTO mapperEntityToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setStatusPost(post.getStatusPost());
        postDTO.setContent(post.getContent());
        postDTO.setDateCreated(getDiffDays(post.getDateCreated(), new Date()));
        postDTO.setUser(post.getUser());
        return postDTO;
    }

    @Override
    public List<PostResponseDto> mapperListPostEntityToDto(List<Post> posts) {
        List<PostResponseDto> postDTOList = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            postDTOList.add(new PostResponseDto(mapperEntityToDTO(posts.get(i)),
                    this.likePostService.totalLikeOfPost(posts.get(i).getId()),
                    this.commentService.mapperCommentEntityToDto(this.commentService.findAllCommentOfPost(posts.get(i).getId())),
                    this.commentService.totalComment(posts.get(i).getId()),
                    this.imageService.findAllPostOfPost(posts.get(i).getId())
                    ));
        }
        return postDTOList;
    }

    @Override
    public List<Post> findAllPostByUser(Long userId) {
        return this.postRepository.findAllPostByUser(userId);
    }

    @Override
    public PostResponseDto findPostById(Long postId) {
        PostResponseDto postResponseDto = new PostResponseDto();
        Optional<Post> postOptional = this.postRepository.findById(postId);
        postResponseDto.setPostDTO(mapperEntityToDTO(postOptional.get()));
        postResponseDto.setImages(this.imageService.findAllPostOfPost(postId));
        return postResponseDto;
    }

    @Override
    public List<Post> findAllPostOfFriends(Long userId) {
        return this.postRepository.findAllPostOfFriends(userId);
    }

    @Override
    public List<Post> findAllPostAboutFriend(Long userId) {
        return this.postRepository.findAllPostAboutFriend(userId);
    }

    @Override
    public List<Post> findAllPostAboutUser(Long userId) {
        return this.postRepository.findAllPostAboutUser(userId);
    }
}
