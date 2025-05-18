<template>
  <form @submit.prevent="handleSubmit">
    <h2>Register</h2>
    <label>Email:</label>
    <input type="email" v-model="email" required />
    <label>Password:</label>
    <input type="password" v-model="password" required />
    <label>Confirm Password:</label>
    <input type="password" v-model="confirmPassword" required />
    <button type="submit">Register</button>
  </form>
</template>

<script>
export default {
  name: 'RegisterForm',
  data() {
    return {
      email: '',
      password: '',
      confirmPassword: ''
    };
  },
  methods: {
    handleSubmit() {
      if (this.password !== this.confirmPassword) {
        alert('Passwords do not match');
        return;
      }
      // TODO: Implement registration logic, e.g., API call
      console.log('Register:', { email: this.email, password: this.password });
    }
  }
};
</script>
