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
  const isPaused = useRef(false);
  const p1Direction = useRef(1);
  const p2Direction = useRef(1);

  const player1Y = useRef(300);
  const player2Y = useRef(300);
  const playerspeed = 20;
  const spriteSize = 300;

  const ballX = useRef(800);
  const ballY = useRef(450);
  const ballSize = 20;
  const ballVelocityX = useRef(0);
  const ballVelocityY = useRef(0);
  const ballSpeed = 10;

  const handleScore = (player: Player) => {
    if (isPaused.current) return;
    
    isPaused.current = true;
    matchService.scorePoint(match.id, player.id);
  
    setTimeout(() => {
      resetBall();
      isPaused.current = false;
    }, 2000);
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

    const handleInput = () => {
      const keys = keysPressed.current;
      if (!keys) return;

        if (keys['KeyW']) { 
          player1Y.current -= playerspeed; 
          p1Direction.current = -1;
        }
        if (keys['KeyS']) { 
          player1Y.current += playerspeed; 
          p1Direction.current = 1;
        }

        if (keys['KeyI']) { 
          player2Y.current -= playerspeed; 
          p2Direction.current = -1; 
        }
        if (keys['KeyK']) { 
          player2Y.current += playerspeed; 
          p2Direction.current = 1; 
        }

      checkPlayerWithinBorders(player1Y, canvas);
      checkPlayerWithinBorders(player2Y, canvas);
    };

    const moveBall = () => {
      ballX.current += ballVelocityX.current;
      ballY.current += ballVelocityY.current;

      if (ballY.current <= 0 || ballY.current >= canvas.height - ballSize) {
        ballVelocityY.current *= -1;
      }

      if (ballX.current <= 0) handleScore(match.player2);
      if (ballX.current >= canvas.width) handleScore(match.player1);
    };

    const checkCollisions = () => {
      const p1HitX = 150;
      const p2HitX = 1350;

      const hitP1 = ballX.current <= p1HitX && 
                    ballY.current > player1Y.current && 
                    ballY.current < player1Y.current + spriteSize;

      const hitP2 = ballX.current >= p2HitX && 
                    ballY.current > player2Y.current && 
                    ballY.current < player2Y.current + spriteSize;

      if (hitP1) {
        ballVelocityX.current = Math.abs(ballVelocityX.current) * 1.1;
        ballX.current = p1HitX + 1;
      }

      if (hitP2) {
        ballVelocityX.current = -Math.abs(ballVelocityX.current) * 1.1;
        ballX.current = p2HitX - ballSize - 1;
      }
    };

    const update = () => {
      if (isPaused.current) return;

      handleInput();
      moveBall();
      checkCollisions();
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
        const rotationAngle = 1.5;

        ctx.save();
        ctx.translate(100, player1Y.current + spriteSize / 2);
        ctx.rotate(p1Direction.current === -1 ? -rotationAngle : rotationAngle);
        ctx.scale(1, p1Direction.current);
        ctx.drawImage(img, -spriteSize / 2, -spriteSize / 2, spriteSize, spriteSize);
        ctx.restore();

        ctx.save();
        ctx.translate(1500, player2Y.current + spriteSize / 2);
        ctx.rotate(p2Direction.current === -1 ? rotationAngle : -rotationAngle);
        ctx.scale(-1, p2Direction.current);
        ctx.drawImage(img, -spriteSize / 2, -spriteSize / 2, spriteSize, spriteSize);
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

    resetBall();
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