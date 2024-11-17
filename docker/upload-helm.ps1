# 설정값
$user = "admin"
$pass = "admin"
$filePath = ".\nginxdemo-0.1.0.tgz"  # 현재 디렉토리 기준 상대 경로
$nexusUrl = "http://localhost:8081"
$repository = "helm-hosted"

# API 엔드포인트 URL 수정
$uri = "$nexusUrl/service/rest/v1/components?repository=$repository"

# 파일이 존재하는지 확인
if (-not (Test-Path $filePath)) {
    Write-Host "Error: File not found at path: $filePath"
    Write-Host "Current directory: $(Get-Location)"
    exit 1
}

# 인증 헤더 생성
$pair = "$($user):$($pass)"
$encodedCreds = [System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair))
$headers = @{
    Authorization = "Basic $encodedCreds"
}

# Multipart 폼 데이터 준비
$boundary = [System.Guid]::NewGuid().ToString()
$LF = "`r`n"

# 파일 내용 읽기
$fileBytes = [System.IO.File]::ReadAllBytes((Resolve-Path $filePath))
$encoding = [System.Text.Encoding]::GetEncoding("iso-8859-1")
$fileContent = $encoding.GetString($fileBytes)

# 요청 본문 구성
$bodyLines = (
    "--$boundary",
    "Content-Disposition: form-data; name=`"helm.asset`"; filename=`"$(Split-Path $filePath -Leaf)`"",
    "Content-Type: application/x-gzip",
    "",
    $fileContent,
    "--$boundary--"
) -join $LF

try {
    $response = Invoke-RestMethod `
        -Uri $uri `
        -Method Post `
        -Headers $headers `
        -Body $bodyLines `
        -ContentType "multipart/form-data; boundary=$boundary" `
        -Verbose

    Write-Host "Upload successful!"
} catch {
    Write-Host "Error occurred: $($_.Exception.Message)"
    Write-Host "Status Code: $($_.Exception.Response.StatusCode.value__)"
    Write-Host "Status Description: $($_.Exception.Response.StatusDescription)"
    
    # 자세한 오류 정보 출력
    $rawError = $_.ErrorDetails.Message
    if ($rawError) {
        Write-Host "Raw Error: $rawError"
    }
}