Clone the gtest repo:
git clone https://github.com/google/googletest.git lib/googletest

1. **Generate build system:**

   ```bash
   cmake -S . -B build -G "MinGW Makefiles"
   ```

2. **Build everything:**

   ```bash
   cmake --build build
   ```

3. **Run app:**

   ```bash
   ./build/SocialAppExe
   ```

4. **Run tests manually:**

   ```bash
   ./build/SocialAppTests
   ```

5. **Clean Build**

   ```bash
   rm -r build
   ```
