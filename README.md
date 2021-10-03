# Spring boot dengan Spring Security + JWT

# Setup Database #

* Run mariadb on docker

  run this script in your project location
  ```
  docker run --rm \
  --name userservice-db \
  -e MARIADB_DATABASE=userservice \
  -e MARIADB_USER=person \
  -e MARIADB_PASSWORD=1xylixmaF7b7rTYqqQ2Q \
  -e MARIADB_ROOT_PASSWORD=1xylixmaF7b7rTYqqQ2Q \
  -v "$PWD/userservice-data:/var/lib/mysql" \
  -p 127.0.0.1:3306:3306 \
  mariadb:10
  ```

# Keterangan #

Custom spring security protocol:

* CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter, membuat custom authentication flow:
    * attemptAuthentication -> dipanggil ketika user request authentication, kemudian dihandle oleh
      authenticationmanager
    * successfulAuthentication -> dipanggil ketika user authentication success, kemudian mengembalikan token
* CustomAuthorizationFilter extends OncePerRequestFilter, membuat custom authorization flow:
    * doFilterInternal -> verification token, get info from jwt token and give authorization to user

* @RequiredArgsConstructor Menghasilkan konstruktor dengan argumen yang diperlukan. Pada project ini memiliki fungsi
  yang sama dengan @Autowired.

# Login or Request Token #

`curl --location --request POST 'http://localhost:8080/api/login' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=jim' \
--data-urlencode 'password=1234'`

* Response:
```
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqaW0iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvbG9naW4iLCJleHAiOjE2MzMwOTA5NTAsImVtYWlsIjoidXNlcmVtYWlsIn0.pOftgmGhTuBzdujsvTA6cFFyw2Vmx7QcNhzlyEIZlzk",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqaW0iLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjMzMDkyMTUxfQ.nxKQkqIiyotHe3_HQwbBewcBtS6_tmkAxzJW7gsF-nM"
}
```

# Refresh Token #

`curl --location --request GET 'http://localhost:8080/api/token/refresh' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqaW0iLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjMzMDkyNDQ1fQ.JuJyi2qU1klPnolHH7VxXvnJHvsdHlT1JCmjfPsVF5A'`

* Response:
```
{
     "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqaW0iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvbG9naW4iLCJleHAiOjE2MzMwOTA5NTAsImVtYWlsIjoidXNlcmVtYWlsIn0.pOftgmGhTuBzdujsvTA6cFFyw2Vmx7QcNhzlyEIZlzk",
     "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqaW0iLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjMzMDkyMTUxfQ.nxKQkqIiyotHe3_HQwbBewcBtS6_tmkAxzJW7gsF-nM"
}
```

# Contoh akses API #
`curl --location --request GET 'localhost:8080/api/persons' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqaW0iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvbG9naW4iLCJleHAiOjE2MzMwODkzODcsImVtYWlsIjoidXNlcmVtYWlsIn0.vhuwA3jbBh8QCxhBiRkamMw05t5NusjY1hqMjN7NQ58'`

* Response: 
```
[
    {
        "id": 77,
        "name": "John Travolta",
        "username": "john",
        "roles": [
            {
                "id": 73,
                "name": "ROLE_USER"
            },
            {
                "id": 74,
                "name": "ROLE_MANAGER"
            }
        ]
    },
    {
        "id": 78,
        "name": "Will Smith",
        "username": "will",
        "roles": [
            {
                "id": 74,
                "name": "ROLE_MANAGER"
            }
        ]
    },
    {
        "id": 79,
        "name": "Jim Carry",
        "username": "jim",
        "roles": [
            {
                "id": 75,
                "name": "ROLE_ADMIN"
            }
        ]
    },
    {
        "id": 80,
        "name": "Arnold Schwarzenegger",
        "username": "arnold",
        "roles": [
            {
                "id": 73,
                "name": "ROLE_USER"
            },
            {
                "id": 75,
                "name": "ROLE_ADMIN"
            },
            {
                "id": 76,
                "name": "ROLE_SUPER_ADMIN"
            }
        ]
    }
]
```