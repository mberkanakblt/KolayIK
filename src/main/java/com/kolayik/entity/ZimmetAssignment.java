package com.kolayik.entity;

import com.kolayik.utility.enums.ZimmetStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * "tblzimmet_assignment" tablosuna karşılık gelir.
 * Bir iş istasyonunda, bir personel için atanan eşya kayıtlarını tutar.
 */
@Entity
@Table(name = "tblzimmet_assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZimmetAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Hangi kullanıcıya zimmetlendiği
     * User entity’sinin “tbluser” karşılığı olduğu varsayılıyor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Atama işlemi gerçekleştiği zaman otomatik set edilecek.
     */
    @Column(name = "assigned_date", nullable = false, updatable = false)
    private LocalDateTime assignedDate;

    /**
     * Eşyanın adı (örneğin: "Dell Laptop XPS 13", "Kask Model ABC", vb.)
     * İleride Envanter tablonuz varsa ManyToOne ile bunu referanslayabilirsiniz.
     */
    @Column(name = "item_name", nullable = false, length = 200)
    private String itemName;

    /**
     * Zimmet ataması ilk oluşturulunca otomatik PENDING,
     * daha sonra personel CONFIRM/REJECT edebilir.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ZimmetStatus status;

    /**
     * Personel onaylarken veya reddederken açıklama eklemek isterse,
     * veya admin ekleme sırasında önceden not bırakmak isterse kullanılabilir.
     */
    @Column(name = "note", length = 500)
    private String note;

    /**
     * Yeni bir satır oluşturulmadan hemen önce atama tarihi ve durum set edilsin.
     */
    @PrePersist
    private void prePersist() {
        this.assignedDate = LocalDateTime.now();
        this.status = ZimmetStatus.PENDING;
    }
}