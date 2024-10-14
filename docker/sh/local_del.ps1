# 사용자 이름과 비밀번호 설정
$username = "docker"
$password = "password"

# 자격 증명을 Base64로 인코딩
$pair = '$username:$password'
$bytes = [System.Text.Encoding]::UTF8.GetBytes($pair)
$encodedCredentials = [Convert]::ToBase64String($bytes)

$headers = @{
    'Accept' = 'application/vnd.docker.distribution.manifest.v2+json'
    'Authorization' = 'Basic $encodedCredentials'
}
Invoke-RestMethod -Uri http://localhost:5000/v2/react/manifests/latest -Method GET -Headers $headers

