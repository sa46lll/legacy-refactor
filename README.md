# 목적

1. 기존의 레거시 코드를 리팩토링한다.
2. 리팩토링 이전에 테스트를 작성한다.
3. 테스트가 통과하는지 확인한다.

## 1. 상태 기반 테스트 vs 행위 기반 테스트

### 상태 기반 테스트
객체의 상태를 검증하는 테스트이다. 객체 내부의 상태에 대해 예상값과 실제값을 비교하는 메서드를 의미한다.

```java
@Test
void 주문_처리_후_아이템_수량이_업데이트되는지_확인한다() {
    sut.order("item1", 10);
    
    assertThat(sut.getQuantity("item1")).isEqualTo(10);
}
```

상태 기반 테스트를 작성하는 경우는 다음과 같다.

1. 다양한 입력 값에 대한 출력 값을 검증하는 경우
2. 복잡한 비즈니스 규칙이나 제약 조건을 검증하는 경우

상태 기반 테스트를 사용하게 되면, 테스트를 위해 상태를 드러내야 하는 메서드가 생길 수도 있지만, 행위가 끝난 후에 상태를 직접적으로 검증하기 때문에 테스트의 안정감이 높아질 수 있다.

### 행위 기반 테스트
객체의 행위를 검증하는 테스트이다. 올바른 로직 수행에 대한 판단의 근거로 동작의 수행 여부를 판단한다.

테스트하고자 한 상황이 수행된 뒤 협력 객체(collaborators)의 특정 메서드가 호출되는 것을 검증한다. 스파이 객체나 mock 객체를 사용하여 테스트를 작성하게 된다.

```java
@Test
void 주문_처리_후_결제되는지_확인한다() {
    sut.order("item1", 10);
    
    verify(mockPaymentService).makePayment(anyDouble());
}
```

주로 행위 기반 테스트를 사용하는 이유는 크게 다음과 같다.

1. 메서드의 리턴 값이 없거나 리턴 값의 상태만으로 검증하기 어려운 경우
2. 예상하는 행위들을 시나리오로 만들고, 시나리오대로 행위가 수행되는지 검증하는 경우

행위 기반 테스트를 사용하게 되면, 상태를 드러내는 메서드를 만들지 않아도 되지만, sut에 대한 구현 방식이 드러나게 된다.
또한 행위 검증을 끝냈지만, 상태를 확인한 것은 아니기 때문에 비교적 테스트에 대한 안정감은 낮아질 수 있다.

### 무엇을 사용해야 하는지

상태 기반 테스트와 행위 기반 테스트 중 어떤 것을 사용해야 하는지는 상황에 따라 다르다.
객체 내부 데이터 구조나 속성에 대한 검증이 필요한 경우에는 상태 기반 테스트를, 내부 구현이 자주 변경되는 경우에는 행위 기반 테스트가 더 유연할 수 있다.

더 좋은 테스트란 없다. 테스트하려는 대상과 목적에 따라 적절한 테스트를 사용하자.

## 2. ParameterizedTest

여러 입력에 대한 테스트를 한 번에 실행할 수 있도록 지원하는 애노테이션이다.
ValueSource, NullAndEmptySource, EnumSource 등의 애노테이션을 사용하여 입력을 지정할 수 있다.

ParameterizedTest를 사용하지 않고 여러 입력에 대한 테스트를 작성하게 되면, 다음과 같이 작성하게 된다.

```java
@Test
void orderId_가_null이면_주문이_되지_않는다() {
    sut.processOrder(null);

    verify(orderService, never()).getOrder(anyString());
    verify(paymentService, never()).makePayment(anyDouble());
}

@Test
void orderId_가_빈_문자열이면_주문이_되지_않는다() {
    sut.processOrder("");

    verify(orderService, never()).getOrder(anyString());
    verify(paymentService, never()).makePayment(anyDouble());
}

@Test
void orderId_가_공백이면_주문이_되지_않는다() {
    sut.processOrder(" ");

    verify(orderService, never()).getOrder(anyString());
    verify(paymentService, never()).makePayment(anyDouble());
}
```

ParameterizedTest를 사용하면, 다음과 같이 작성할 수 있다.

```java
@ParameterizedTest
@NullAndEmptySource
@ValueSource(strings = {" ", "  "})
void orderId가_비어있으면_주문이_되지_않는다(String orderId) {
    sut.processOrder(orderId);

    verify(orderService, never()).getOrder(anyString());
    verify(paymentService, never()).makePayment(anyDouble());
}
```

![스크린샷 2023-12-10 000647](https://github.com/sa46lll/legacy-refactor/assets/62706048/dc41880f-6c37-4053-8454-a9c3d1b56784)

ParameterizedTest는 테스트를 간결하게 만들 수 있다. 또한, 여러 타입의 입력도 처리할 수 있어 편리하다.

다만, 너무 남용한다면 테스트가 무엇을 뜻하는지 알기 어려워질 수 있으니 적절하게 사용하자.
