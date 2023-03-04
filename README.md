# 청과물 가격 조회 서비스
청과물 API 중계 서버를 구현한다.

## 개발환경
- spring boot 2.7.9
- jdk 11

## 기능 구현 목록
- [x] 청과물 분류와 이름을 입력하여 가격을 조회할 수 있다.
- [x] 청과물 가격 조회는 아래 4가지 단계를 거치며 처리되어야 한다. 각 단계 실패시 다음 단계를 시도한다.
  - [x] 캐시 탐색
  - [x] 외부 API 호출 (retry)
  - [x] DB 데이터 호출 (recover)
  - [x] 오류 안내 메시지 반환
- [x] 청과물 가격 조회 엔드포인트로부터 들어오는 데이터는 검증되어야 한다.

## 핵심 구현 기술
- API retry
- Application Cache
