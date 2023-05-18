<template>
  <div class="card">
    <div class="container group">
      <div class="top-matter">
        <div class="title">
          <h1>一个交流的地方
            <span @click="chatRoom">开始聊天</span>
          </h1>
        </div>
      </div>
    </div>
  </div>
</template>

<script  lang='ts' setup>
import { onMounted, } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const chatRoom = () => {
  router.replace({ path: "/chat" });
}
onMounted(() => { });
</script>

<style lang='less' scoped>
.card {
  margin-top: 15em;
  width: 100%;
  height: 500px;
  box-sizing: border-box;
}

a {
  text-decoration: none;
}

.container {
  background-image: url(https://tva1.sinaimg.cn/large/005DW9w3ly1grk8572c3cj32yo1uo4qp.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
}

.top-matter {
  padding: 10em;
  float: right;
  max-width: 400px;

  * {
    color: rgb(255, 255, 240);
  }

  padding-right: 20em;
}
.title{
  cursor: pointer;
}

h1 {
  font-size: 3rem;
  line-height: 1;
  margin-bottom: 24px;
  text-align: right;
  direction: rtl;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.7);
  font-family: "News Cycle", sans-serif;

  span {
    display: block;
    text-transform: uppercase;
    font-size: 6rem;
  }
}
</style>