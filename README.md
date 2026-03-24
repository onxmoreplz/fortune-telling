# 🔮 오늘의 운세

> 사용자의 개인 정보를 바탕으로 오늘의 운세 정보를 제공하는 Android 앱

<br>

## 📱 주요 기능

- 이름, 생년월일, 태어난 시간(선택), 태어난 지역 입력
- AI 기반 오늘의 운세 생성
   - 행운 지수, 피하면 좋을 음식/행동, 추천 옷 색상 제공 
  - 행운 아이템, 행운 숫자, 행운 방향, 행운 메시지 제공

<br>


## 📁 모듈 구조

```
Fortune
├── app                  # 앱 진입점, Navigation 설정
├── feature
│   ├── home             # 운세 결과 화면, 사용자 정보 입력 화면, 로딩 화면
│   ├── login            # 로그인 화면
│   └── splash           # 스플래시 화면
└── core
    ├── data             # Repository 구현체
    ├── domain           # UseCase, Repository 인터페이스
    ├── model            # 공통 데이터 모델, Route 상수
    ├── network          # API 통신
    └── designsystem     # 공통 디자인 컴포넌트
```

<br>

## 📸 스크린샷

| 사용자 정보 입력 | 로딩 | 운세 결과 |
|:---:|:---:|:---:|
| <img width="320" src="https://github.com/user-attachments/assets/209c63c6-10f1-41ff-a48a-3ea5ca75cc13" /> | <img width="320" src="https://github.com/user-attachments/assets/bc938983-0c2b-4cd7-8877-9a96cf070a08" /> | <img width="320" src="https://github.com/user-attachments/assets/18a6b82c-3beb-4bb9-8fb4-422e7c0e7e1e" /> |
