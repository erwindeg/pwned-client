# Very simple 'Have I been PWNED' Java client.
Uses the ```https://api.pwnedpasswords.com/range/``` API call to check whether a password has been exposed in a data breach 
according to https://haveibeenpwned.com.

## Usage

```java
PwndAPI pwndAPI = new PwndAPI();
boolean usedInBreach = pwndAPI.haveIBeenPwned("password");
```

## Dependency

**Gradle**

**Maven**

