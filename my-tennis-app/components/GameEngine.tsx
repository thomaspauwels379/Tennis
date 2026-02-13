"use client";

import { useEffect, useRef } from 'react';

export default function GameEngine() {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;
  }, []);

  return (
    <canvas 
      ref={canvasRef}
      className="w-full h-full"
      width={1600} 
      height={900}
    />
  );
}