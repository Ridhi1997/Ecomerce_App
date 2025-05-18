<template>
  <form @submit.prevent="handleLogin">
    <label>User Name:</label>
    <input type="text" v-model="username" required />

    <label>Password:</label>
    <input type="password" v-model="password" required />

    <button type="submit">Login</button>

    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </form>
</template>

<script>
export default {
  name: 'LoginForm',
  data() {
    return {
      username: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        const response = await fetch('http://localhost:8081/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password
          })
        });

        if (!response.ok) {
          const errorData = await response.json();
          this.errorMessage = errorData.message || 'Login failed';
          return;
        }

        const data = await response.json();
        // Handle successful login, e.g., store token, redirect
        console.log('Login successful:', data);
        this.errorMessage = '';
      } catch (error) {
        console.error('Error during login:', error);
        this.errorMessage = 'An error occurred during login. Please try again.';
      }
    }
  }
};
</script>

<style scoped>
.error {
  color: red;
  margin-top: 10px;
}
</style>
