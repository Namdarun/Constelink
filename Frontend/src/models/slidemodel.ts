export interface SliderSettings {
    dots: boolean;
    infinite: boolean;
    speed: number;
    slidesToShow: number;
    slidesToScroll: number;
    draggable: boolean;
    touchMove: boolean;
    fade: boolean;
    autoplay: true, // 자동 슬라이드 이동을 활성화합니다.
    autoplaySpeed: number, // 슬라이드가 자동으로 이동하는 시간을 설정합니다(밀리초).
    arrows: false,
  }