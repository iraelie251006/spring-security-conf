# Spring Security (Asymetric encryption)
Used RSA for generating public & private keys

N.B: Never share the private key, but you can share public key

## Commands Used
### Private key
```bash
 openssl genpkey -algorithm RSA -out private-key.pem -pkeyopt rsa_keygen_bits:2048
```

### Public key
```bash
openssl rsa -pubout -in private-key.pem -out public_key.pem  
```