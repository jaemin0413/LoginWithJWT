package study.LoginWithJWT.global.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//상속 전용 클래스에 붙여주는 어노테이션
@EntityListeners(AuditingEntityListener.class)
// 엔티티의 생성, 수정을 감지하는 어노테이션
public abstract class BaseEntity {
    // 인스턴스화 할 일이 없으므로 추상클래스로 선언

    @CreatedDate
    // 자동으로 해당 시간을 삽입해줌
    @JsonFormat(timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(timezone ="Asia/seoul")
    private LocalDateTime updatedAt;

    @Column(name="deleted_at")
    @JsonFormat(timezone = "Asia/Seoul")
    private LocalDateTime deletedAt;
}
