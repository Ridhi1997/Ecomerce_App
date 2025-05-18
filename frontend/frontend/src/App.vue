<template>
  <div>
    <nav>
      <button @click="isLogin = true">Login</button>
      <button @click="isLogin = false">Register</button>
    </nav>
    <component :is="isLogin ? 'LoginForm' : 'RegisterForm'" />
  </div>
</template>

<script>
import LoginForm from './components/LoginForm.vue';
import RegisterForm from './components/RegisterForm.vue';

export default {
  name: 'App',
  components: {
    LoginForm,
    RegisterForm
  },
  data() {
    return {
      isLogin: true
    };
  }
};
</script>
