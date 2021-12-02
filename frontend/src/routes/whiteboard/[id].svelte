<script lang="ts">
  import draw from './draw';

  let canvas: HTMLCanvasElement;

  const clear = () => {
    if (!canvas) return;

    const ctx = canvas.getContext('2d');

    if (!ctx) return;

    ctx.clearRect(
      0,
      0,
      canvas.width,
      canvas.height
    );
  };

  // const save = () => {
  //   if (!canvas) return;

  //   const href = canvas.toDataURL();
  //   gallery.update((prev) => [
  //     ...prev,
  //     href,
  //   ]);
  // };
</script>

<svelte:head>
	<title>Drawin - Whiteboard</title>
</svelte:head>

<header>
  <button
    type="button"
    on:click={clear}
  >
    Clear
  </button>
  <!-- <button type="button" on:click={save}>
    Save
  </button> -->
</header>

<main>
  <canvas bind:this={canvas} use:draw />
</main>

<style>
  header {
    width: 80%;
    margin: 16px auto;
  }
  button {
    border: none;
    font: inherit;
    cursor: pointer;
    appearance: none;
    padding: 12px 16px;
    border-radius: 8px;
    background: rgba(
      255,
      255,
      255,
      0.2
    );
    border: solid 1px rgba(0, 0, 0, 0.2);
    transition: background 0.3s
      cubic-bezier(0.25, 0.1, 0.25, 1);
  }
  button:hover {
    background: rgba(
      255,
      255,
      255,
      0.3
    );
  }
  main {
    width: 80%;
    margin: 0 auto;
  }
  div.gallery {
    display: flex;
    margin: 32px 0;
    overflow: auto;
  }
  button.saved {
    margin: 0 8px;
  }
  div.gallery button:first-of-type {
    margin-left: 0;
  }
  div.gallery button:last-of-type {
    margin-right: 0;
  }
  img {
    height: 100px;
    display: block;
    border-radius: 8px;
    background: white;
    border: solid 1px rgba(0, 0, 0, 0.2);
  }
  canvas {
    cursor: url(data:text/plain;charset=utf-8;base64,8J+WjA==);
    display: block;
    margin: 0 auto;
    touch-action: none;
    background: white;
    border-radius: 32px;
    border: solid 1px #ccc;
  }
</style>
