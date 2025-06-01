package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.ZimmetConfirmRequestDto;
import com.kolayik.dto.request.ZimmetCreateRequestDto;
import com.kolayik.dto.request.ZimmetUpdateRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.dto.response.ZimmetDto;
import com.kolayik.exception.KolayIkException;
import com.kolayik.exception.ErrorType;
import com.kolayik.repository.UserRepository;
import com.kolayik.service.IZimmetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kolayik.config.RestApis.ZIMMET;

/**
 * Zimmet yönetimi controller’ı:
 * - Admin: ekleme, güncelleme, silme, listeleme, tek kayıt getirme
 * - Personel: kendi zimmetini listeleme ve onaylama/reddetme
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ZIMMET)
@CrossOrigin("*")
public class ZimmetController {

    private final IZimmetService zimmetService;
    private final UserRepository userRepo;
    private final JwtManager jwtManager;

    /**
     * 1) Admin: Yeni bir zimmet ataması yapar.
     *    POST /dev/v1/zimmet/add-zimmet
     *
     * Body örneği:
     * {
     *   "userEmail": "personel@ornek.com",
     *   "itemName": "Dell XPS 13 Laptop",
     *   "note": "Acil ihtiyaç"
     * }
     */
    @PostMapping("/add-zimmet")
    public ResponseEntity<BaseResponse<ZimmetDto>> addZimmet(@RequestBody ZimmetCreateRequestDto dto) {
        ZimmetDto created = zimmetService.createZimmet(dto);
        return ResponseEntity.ok(
                BaseResponse.<ZimmetDto>builder()
                        .code(200)
                        .message("Zimmet ataması başarılı.")
                        .data(created)
                        .build()
        );
    }

    /**
     * 2) Admin: Var olan bir zimmet kaydını günceller.
     *    PUT /dev/v1/zimmet/update-zimmet
     *
     * Body örneği (örneğin ID değiştiğinde):
     * {
     *   "id": 12,
     *   "userId": 7,
     *   "itemName": "Samsung Kask Model Y",
     *   "note": "Yenileme istendi"
     * }
     */
    @PutMapping("/update-zimmet")
    public ResponseEntity<BaseResponse<ZimmetDto>> updateZimmet(@RequestBody ZimmetUpdateRequestDto dto) {
        ZimmetDto updated = zimmetService.updateZimmet(dto);
        return ResponseEntity.ok(
                BaseResponse.<ZimmetDto>builder()
                        .code(200)
                        .message("Zimmet kaydı güncellendi.")
                        .data(updated)
                        .build()
        );
    }

    /**
     * 3) Admin: Bir zimmet kaydını silmek isterse.
     *    DELETE /dev/v1/zimmet/delete-zimmet/{id}
     */
    @DeleteMapping("/delete-zimmet/{id}")
    public ResponseEntity<BaseResponse<Boolean>> deleteZimmet(@PathVariable Long id) {
        zimmetService.deleteZimmet(id);
        return ResponseEntity.ok(
                BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Zimmet kaydı silindi.")
                        .data(true)
                        .build()
        );
    }

    /**
     * 4) Admin: Tüm zimmet kayıtlarını listeleme.
     *    GET /dev/v1/zimmet/get-all-zimmet
     */
    @GetMapping("/get-all-zimmet")
    public ResponseEntity<BaseResponse<List<ZimmetDto>>> getAllZimmet() {
        List<ZimmetDto> list = zimmetService.getAllZimmet();
        return ResponseEntity.ok(
                BaseResponse.<List<ZimmetDto>>builder()
                        .code(200)
                        .message("Tüm zimmet listesi.")
                        .data(list)
                        .build()
        );
    }

    /**
     * 5) Admin: Tek bir zimmet kaydını ID ile getirme.
     *    GET /dev/v1/zimmet/get-zimmet/{id}
     */
    @GetMapping("/get-zimmet/{id}")
    public ResponseEntity<BaseResponse<ZimmetDto>> getZimmetById(@PathVariable Long id) {
        ZimmetDto dto = zimmetService.getZimmetById(id);
        return ResponseEntity.ok(
                BaseResponse.<ZimmetDto>builder()
                        .code(200)
                        .message("Zimmet detayı.")
                        .data(dto)
                        .build()
        );
    }

    /**
     * 6) Personel: Kendi zimmetlerini listeleme.
     *    GET /dev/v1/zimmet/get-my-zimmet?userToken=eyJhbGci...
     */
    @GetMapping("/get-my-zimmet")
    public ResponseEntity<BaseResponse<List<ZimmetDto>>> getMyZimmet(@RequestParam("userToken") String userToken) {
        Optional<Long> optionalUserId = jwtManager.validateToken(userToken);
        if (optionalUserId.isEmpty()) {
            throw new KolayIkException(ErrorType.INVALID_TOKEN);
        }
        Long userId = optionalUserId.get();
        List<ZimmetDto> myList = zimmetService.getZimmetByUserId(userId);
        return ResponseEntity.ok(
                BaseResponse.<List<ZimmetDto>>builder()
                        .code(200)
                        .message("Kendi zimmetleriniz.")
                        .data(myList)
                        .build()
        );
    }

    /**
     * 7) Personel: Kendi zimmetini onaylama veya reddetme.
     *    PUT /dev/v1/zimmet/confirm-zimmet?userToken=eyJhbGci...
     *
     * Body örneği:
     * {
     *   "id": 7,
     *   "status": "CONFIRMED",
     *   "note": "Teslim aldım, teşekkürler."
     * }
     * veya
     * {
     *   "id": 7,
     *   "status": "REJECTED",
     *   "note": "Cihaz hasarlı, yenisini istiyorum."
     * }
     */
    @PutMapping("/confirm-zimmet")
    public ResponseEntity<BaseResponse<ZimmetDto>> confirmZimmet(
            @RequestBody ZimmetConfirmRequestDto dto,
            @RequestParam("userToken") String userToken
    ) {
        Optional<Long> optionalUserId = jwtManager.validateToken(userToken);
        if (optionalUserId.isEmpty()) {
            throw new KolayIkException(ErrorType.INVALID_TOKEN);
        }
        Long userId = optionalUserId.get();

        // “Bu kaydın gerçekten userId’ye ait olup olmadığını kontrol et.”
        ZimmetDto current = zimmetService.getZimmetById(dto.getId());
        if (!current.getUserId().equals(userId)) {
            throw new KolayIkException(ErrorType.FORBIDDEN);
        }

        ZimmetDto confirmed = zimmetService.confirmZimmet(dto);
        return ResponseEntity.ok(
                BaseResponse.<ZimmetDto>builder()
                        .code(200)
                        .message("Zimmet durumu güncellendi.")
                        .data(confirmed)
                        .build()
        );
    }
}