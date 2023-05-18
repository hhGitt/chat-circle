import { fileURLToPath, URL } from 'node:url'

import { defineConfig  } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';

// https://vitejs.dev/config/
export default defineConfig({
//配置根目录, 跨域
 base: './',
 server: {
   proxy: {
     '/user': {
       target: 'http://localhost:8080/',
       changeOrigin: true,
       rewrite: (path) => path.replace(/^\/api/, '')
     }
   }
 },
 plugins: [
   vue(),
   //动态按需引入element plus组件
   AutoImport({
     resolvers: [
       ElementPlusResolver(),
     ],
   }),
   Components({
     resolvers: [
       ElementPlusResolver(),
     ],
   }),
   vueJsx(),
 ],
 resolve: {
   alias: {
     '@': fileURLToPath(new URL('./src', import.meta.url))
   }
 },
 //打包配置
 build: {
   emptyOutDir: true,
 }
})

