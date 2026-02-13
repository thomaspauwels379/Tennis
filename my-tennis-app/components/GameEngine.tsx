"use client";

import { matchService } from '@/services/matchService';
import { Match, Player } from '@/types';
import { RefObject, useEffect, useRef } from 'react';

type GameProps = {
  keysPressed: React.RefObject<Record<string, boolean> | null>;
  match: Match;
};

export default function GameEngine({ keysPressed, match }: GameProps) {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const player1Y = useRef(300);
  const player2Y = useRef(300);
  const speed = 20;
  const spriteSize = 300;

  const ballX = useRef(800);
  const ballY = useRef(450);
  const ballSize = 20;
  const ballVelocityX = useRef(0);
  const ballVelocityY = useRef(0);
  const ballSpeed = 10;

  const handleScore = (player: Player) => {
    matchService.scorePoint(match.id ,player.id);
    resetBall();
  };

  const resetBall = () => {
    ballX.current = 800;
    ballY.current = 450;
    const directionX = Math.random() > 0.5 ? 1 : -1;
    const directionY = (Math.random() * 2 - 1);
    
    ballVelocityX.current = directionX * ballSpeed;
    ballVelocityY.current = directionY * ballSpeed;
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    const img = new Image();
    img.src = "/personWithRacket.svg";

    let animationFrameId: number;

    resetBall();

    const update = () => {
      const keys = keysPressed.current;
      if (!keys) return;

      if (keys['KeyW']) player1Y.current -= speed;
      if (keys['KeyS']) player1Y.current += speed;
      
      if (keys['KeyI']) player2Y.current -= speed;
      if (keys['KeyK']) player2Y.current += speed;

      checkPlayerWithinBorders(player1Y, canvas);
      checkPlayerWithinBorders(player2Y, canvas);

      ballX.current += ballVelocityX.current;
      ballY.current += ballVelocityY.current;

      if (ballY.current <= 0 || ballY.current >= canvas.height - ballSize) {
        ballVelocityY.current *= -1;
      }

      if (ballX.current <= 0) {
        handleScore(match.player2);
      } else if (ballX.current >= canvas.width - ballSize) {
        handleScore(match.player1);
      }
    };

    const checkPlayerWithinBorders = (player:RefObject<number>,canvas:HTMLCanvasElement) => {
      if (player.current < 0) player.current = 0;
      if (player.current > canvas.height - spriteSize) {
        player.current = canvas.height - spriteSize;
      }
    };

    const draw = () => {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      
      ctx.fillStyle = "#00FFCC";
      ctx.fillRect(ballX.current, ballY.current, ballSize, ballSize);

      if (img.complete) {
        ctx.drawImage(img, -50, player1Y.current, spriteSize, spriteSize);

        ctx.save();
        ctx.translate(1600, 0);
        ctx.scale(-1, 1);
        ctx.drawImage(img, -50, player2Y.current, spriteSize, spriteSize);
        ctx.restore();
      } else {
        img.onload = () => draw();
      }
    };

    const loop = () => {
      update();
      draw();
      animationFrameId = requestAnimationFrame(loop);
    };

    loop();

    return () => cancelAnimationFrame(animationFrameId);
  }, [keysPressed]);

  return (
    <canvas 
      ref={canvasRef}
      className="w-full h-full"
      width={1600} 
      height={900}
    />
  );
}