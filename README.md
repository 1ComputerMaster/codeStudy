# 유레카 서버 + API 게이트웨이 기반의 MSA 스프링 부트 프로젝트

## 소개

이 프로젝트는 스프링 부트를 사용하여 유레카 서버와 API 게이트웨이를 주요 구성 요소로 하는 마이크로서비스 아키텍처(MSA)를 간단히 구현한 것입니다. 현재 이 프로젝트는 **유저 서비스**라는 단일 서비스 계층을 포함하고 있습니다. 이 프로젝트의 목적은 마이크로서비스, 서비스 디스커버리, API 관리의 개념을 이해하고 적용하기 위해 진행 하였습니다.

## 아키텍처 개요

이 프로젝트는 다음과 같은 구성 요소로 구성됩니다:

1. **유레카 서버**: 모든 마이크로서비스가 등록되는 서비스 레지스트리 역할을 합니다. 이는 서비스 끼리 동적으로 등록되고 통신 할 수 있는 지점을 만들어 줍니다.
   
2. **API 게이트웨이**: 클라이언트 요청의 진입점으로서, 유레카 서버가 제공하는 서비스 레지스트리를 기반으로 적절한 마이크로서비스로 요청을 라우팅합니다. 또한 인증 서비스 처리등을 진행하여 첫 번째 필터의 역할을 합니다.

3. **유저 서비스**: 사용자 관련 작업을 처리하는 기본 마이크로서비스입니다. 이 서비스는 유레카 서버에 등록되며, API 게이트웨이를 통해 접근할 수 있습니다.

4. **헥사고날 아키텍처**: 유저 서비스는 헥사고날 아키텍처를 따라 구현되었습니다. 이 설계 방식으로 인해서 외부 요인의 의존성을 낮추고 패키지간의 역할을 분명히하여 특정 지점에 대해서만 집중해서 개발 할 수 있습니다.

## 프로젝트 구조

- **유레카 서버**: 모든 마이크로서비스가 등록되는 중앙 서비스 서버 입니다.
- **API 게이트웨이**: 클라이언트 요청을 적절한 마이크로서비스로 라우팅합니다.
- **유저 서비스**: 헥사고날 아키텍처로 구현된 사용자 관리 작업을 처리합니다.

### 유레카 서버

유레카 서버는 독립형 서비스로 설정되어 있으며, 유저 서비스를 등록하여 서비스 등록 및 로드 밸런싱을 가능하게 합니다.

### API 게이트웨이

API 게이트웨이는 유레카 서버가 유지하는 서비스 레지스트리를 기반으로 유저 서비스로의 요청을 라우팅하도록 설정되었습니다. 이는 클라이언트로부터 내부 마이크로서비스 아키텍처를 추상화합니다.

### 유저 서비스

유저 서비스는 사용자 관리에 대한 RESTful 엔드포인트를 제공하는 간단한 스프링 부트 애플리케이션입니다. 이 서비스는 헥사고날 아키텍처를 사용하여 설계되었으며, 핵심 비즈니스 로직이 데이터베이스나 API 게이트웨이와 같은 주변 인프라로부터 분리됩니다. 이 서비스는 유레카 서버에 등록되어 API 게이트웨이에 의해 발견될 수 있습니다.

## 배운 점

이 프로젝트를 통해 다음과 같은 개념에 대한 실무 경험을 얻었습니다:

1. **유레카를 통한 서비스 디스커버리**: 유레카를 사용하여 마이크로서비스들이 동적으로 서로를 발견하고 통신할 수 있는 방법을 배웠습니다. 이 접근 방식은 분산 시스템에서 서비스 등록과 디스커버리의 복잡성을 관리하는 데 도움이 됩니다.

2. **단일 진입점으로서의 API 게이트웨이**: API 게이트웨이를 구현함으로써 다양한 마이크로서비스로 클라이언트 요청을 관리하고 라우팅하는 방법을 이해할 수 있었습니다. 또한, 인증, 로깅, 모니터링과 같은 공통 관심사를 중앙에서 처리하여 전체 아키텍처를 단순화하는 방법도 배웠습니다.

3. **마이크로서비스 간 통신**: 마이크로서비스 아키텍처에서 서비스 간의 통신을 이해하는 것이 중요했습니다. 서비스 격리, 확장성 및 장애 허용의 중요성을 배웠습니다.

4. **헥사고날 아키텍처**: 유저 서비스를 헥사고날 아키텍처로 구현함으로써 핵심 비즈니스 로직을 외부 시스템과 격리하는 방법을 이해하게 되었습니다. 이 접근 방식은 서비스의 유연성을 높여 변화하는 요구 사항에 쉽게 적응하고 테스트 용이성을 개선할 수 있게 합니다.

5. **스프링 클라우드 컴포넌트**: 마이크로 서비스 기반 환경에서의 유레카 서버와 API 게이트웨이와 같은 스프링 클라우드 기반의 컴포넌트를 사용하여 경험을 다졌습니다.


## 결론
이 프로젝트는 스프링 부트, 유레카, API 게이트웨이, 그리고 헥사고날 아키텍처를 사용한 마이크로서비스 아키텍처에 대한 탄탄한 기초를 다질 수 있게 해주었습니다. 이 아키텍처를 구축하고 배포하면서 얻은 실무 경험은 앞으로 더 복잡한 마이크로서비스 기반 시스템을 탐구하고 구현하는 데 거름이 될 것 입니다.