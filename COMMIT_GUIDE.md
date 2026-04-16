# COMMIT GUIDE - Cosmetic Shop BE

Tai lieu nay huong dan quy trinh lam viec Git/GitHub cho tat ca thanh vien khi clone du an va bat dau lam chuc nang moi.

## 1) Luong nhanh can nho

develop -> feature -> develop -> staging -> main

- Tuyet doi khong code truc tiep tren main.
- Tuyet doi khong code truc tiep tren develop.
- Luon tao feature branch, commit ro rang, qua PR + review.

## 2) Chuan bi sau khi clone # CLONE LẦN ĐẦU TIÊN.


## NẾU ĐÃ CLONE RỒI THÌ BẮT ĐẦU TỪ ĐÂY.
Chay cac lenh sau de dong bo code moi nhat truoc khi bat dau:

```bash
git checkout develop
git pull origin develop
```

Muc tieu:
- Dam bao dang dung code moi nhat.
- Giam toi da conflict khi mo PR.

## 3) Tao branch cho chuc nang moi

Tao branch tu develop:

```bash
git checkout -b feature/login-api
```

Quy tac dat ten branch:
- feature/<ten-chuc-nang>
- fix/<ten-loi>
- hotfix/<ten-loi-khan-cap>

Vi du:
- feature/login
- feature/payment-api
- fix/cart-total

## 4) Code va test local (bat buoc)

Trong qua trinh phat trien:
- Viet code theo coding convention cua du an.
- Chay test local truoc khi commit.
- Neu la API moi/sua API, can test endpoint bang Postman/Swagger va kiem tra log loi.

Checklist truoc commit:
- Code build thanh cong.
- Test lien quan pass.
- Khong de file tam, debug log, secret.

## 5) Commit dung chuan

Thuc hien:

```bash
git add .
git commit -m "feat: add login API"
```

Quy uoc commit message:
- feat: them chuc nang moi.
- fix: sua bug.
- refactor: tai cau truc, toi uu code khong doi behavior.
- docs: cap nhat tai lieu.
- test: bo sung/chinh sua test.
- chore: viec phu tro (config, script, dependency...).

Mau khuyen nghi:
- <type>: <mo ta ngan gon o thi hien tai>
- Vi du:
  - feat: add login API
  - fix: handle null customer address
  - refactor: extract payment validation service
  - docs: update API auth flow

Luu y:
- Mot commit nen tap trung mot muc tieu ro rang.
- Tranh commit qua lon, kho review.

## 6) Push branch len remote

```bash
git push origin feature/login-api
```

## 7) Tao Pull Request vao develop

Tao PR theo huong:
- feature/login-api -> develop
- Thực hiện trên github

PR can co day du:
- Lam gi: mo ta thay doi chinh.
- Vi sao: ly do business/technical.
- Test nhu the nao: test case da chay, ket qua.
- Anh huong: module nao bi tac dong, co breaking change hay khong.

Bat buoc:
- Gan reviewer (team lead/senior).
- Day du screenshots/log neu can thiet.

## 8) Code review va cap nhat

Reviewer se:
- Xem code.
- Comment loi/rui ro/goi y cai thien.

Nguoi thuc hien:
- Sua theo comment.
- Commit bo sung vao cung branch feature.
- Push lai de cap nhat PR.


## PHÂN DEPLOY
## 9) Merge vao develop

Khi PR duoc approve va pass check:
- Merge PR vao develop.

Sau merge:
- Feature da vao nhanh chung.


## 10) Deploy Staging (QA)

- Day tu develop -> staging.
- Tester thuc hien QA.

Neu phat hien loi:
- Tao nhanh fix tu develop.
- Lap lai quy trinh feature/fix -> PR -> review -> merge develop.

## 11) Release Production

- Day tu staging -> main.
- Deploy production theo quy trinh release cua team.

## 12) Cac loi thuong gap can tranh

Khong lam:
- Code truc tiep tren main.
- Code truc tiep tren develop.
- Push code chua test.
- Commit message mo ho (update, fix bug, ...).

Nen lam:
- Luon bat dau bang viec dong bo develop.
- Chia nho commit, message ro rang.
- Luon qua PR va review truoc khi merge.

## 13) Lenh mau cho mot vong lam viec day du

```bash
# 1) Dong bo code moi nhat
git checkout develop
git pull origin develop

# 2) Tao nhanh feature
git checkout -b feature/login-api

# 3) Code + test local

# 4) Commit
git add .
git commit -m "feat: add login API"

# 5) Push
git push origin feature/login-api

# 6) Tao PR: feature/login-api -> develop
```

---

Neu can thay doi quy trinh (squash merge, convention commit nang cao, quy tac release), cap nhat file nay va thong bao lai cho toan bo team.