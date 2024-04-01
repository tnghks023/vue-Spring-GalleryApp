<template>
<Header/>
<RouterView/>
<Footer/>
</template>

<script>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import store from '@/scripts/store';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { watch } from 'vue';

export default {
  name: 'App',
  components: {
    Header,
    Footer
    
  }, setup(){
    const check = () => {
      axios.get("/api/account/check").then(({data}) =>  {
        console.log(data);
        store.commit("setAccount", data || 0);
      })
    }

    const route = useRoute();

    watch(route, () => {
      check();
    })
  }
}
</script>

<style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
</style>
