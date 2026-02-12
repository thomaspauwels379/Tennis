"use client";

import { useEffect, useRef } from 'react';

export default function GameEngine() {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    // Hier komt later je teken-logica (ctx.draw...)
    
  }, []);

  return (
    <canvas 
      ref={canvasRef}
      className="w-full h-full"
      // We geven een standaard resolutie mee, 
      // de CSS (w-full/h-full) zorgt voor de schaling
      width={1600} 
      height={900}
    />
  );
}