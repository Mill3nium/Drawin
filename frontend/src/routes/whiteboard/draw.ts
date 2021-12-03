const randomInt = (
  min: number,
  max: number
): number =>
  Math.floor(
    Math.random() * (max - min + 1) +
    min
  );

export const randomColor = (): string =>
  `hsl(${randomInt(0, 360)}, 40%, 70%)`;

const draw = (
  node: HTMLCanvasElement
): { destroy(): void } => {
  const resize = () => {
    node.width =
      Math.min(
        window.innerWidth,
        1024
      ) * 0.8;
    node.height =
      window.innerHeight * 0.8;
  };

  resize();

  window.addEventListener(
    'resize',
    resize
  );

  const handlePointerDown = ({
    clientX,
    clientY,
    currentTarget,
  }: PointerEvent) => {
    const ctx = node.getContext('2d');
    if (!ctx) {
      alert(
        'Canvas is not supported in this browser!'
      );
      return;
    }

    // const color = randomColor();
    const color = { r: 0, b: 0, g: 0, a: 255 }
    // document.body.style.background = `rgba(${color.r}, ${color.g}, ${color.b}, ${color.a})`;

    let prevX, prevY
    const draw = (
      x: number,
      y: number
    ): void => {
      const width = 8;

      if(!prevX && !prevY) {
        prevX = x
        prevY = y
      }

      // https://stackoverflow.com/questions/2368784/draw-on-html5-canvas-using-a-mouse
      ctx.beginPath(); // begin

      ctx.lineWidth = width;
      ctx.lineCap = 'round';
      ctx.strokeStyle = `rgba(${color.r}, ${color.g}, ${color.b}, ${color.a})`

      ctx.moveTo(prevX, prevY); // from
      ctx.lineTo(x, y); // to

      ctx.stroke(); // draw it!

      prevX = x
      prevY = y
    };

    draw(
      clientX - node.getBoundingClientRect().left,
      clientY - node.getBoundingClientRect().top
    );

    const handlePointerMove = ({
      clientX,
      clientY,
      currentTarget,
    }: PointerEvent) => {
      const target = currentTarget as HTMLCanvasElement;

      draw(
        clientX - target.getBoundingClientRect().left,
        clientY - target.getBoundingClientRect().top
      );
    };
    const handlePointerUp = () => {
      node.removeEventListener(
        'pointermove',
        handlePointerMove
      );
      document.removeEventListener(
        'pointerup',
        handlePointerUp
      );
    };

    node.addEventListener(
      'pointermove',
      handlePointerMove
    );
    document.addEventListener(
      'pointerup',
      handlePointerUp
    );
  };

  node.addEventListener(
    'pointerdown',
    handlePointerDown
  );
  return {
    destroy() {
      node.removeEventListener(
        'pointerdown',
        handlePointerDown
      );
      window.removeEventListener(
        'resize',
        resize
      );
    },
  };
};

export default draw;
