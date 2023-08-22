package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUtil<T> {
    // 공통 응답의 DTO
    private boolean sucuess; // true,fail
    private T data; // 댓글쓰기 성공,실패

    public ApiUtil(boolean sucuess, T data) {
        this.sucuess = sucuess;
        this.data = data;
    }
}
