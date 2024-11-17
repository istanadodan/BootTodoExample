import fitz  # PyMuPDF => pip install PyMuPDF
import re
import random


def generate_random_ssn(ssn: str) -> str:
    """일련번호 임의 생성"""
    return ssn[:8] + "".join([str(random.randint(0, 9)) for _ in range(6)])


def replace_ssn_in_pdf(input_pdf, output_pdf):
    # PDF 열기
    pdf_document = fitz.open(input_pdf)

    # 주민등록번호 정규표현식 패턴 (숫자 13자리)
    ssn_pattern = r"\d{6}-\d{7}"

    # 모든 페이지를 순회하면서 주민등록번호를 검색하고 변경
    for page_num in range(pdf_document.page_count):
        page = pdf_document.load_page(page_num)
        text = page.get_text("text")

        # 주민등록번호 찾기
        ssns = re.findall(ssn_pattern, text)

        if ssns:
            # 주민등록번호 위치 검색 및 대체
            for ssn in ssns:
                random_ssn = generate_random_ssn(ssn)
                areas = page.search_for(ssn)

                # 주민등록번호를 임의의 숫자로 대체
                for area in areas:
                    x0, y0, x1, y1 = area  # area는 (x0, y0, x1, y1) 좌표
                    # 기존 텍스트 삭제
                    page.draw_rect(
                        (x0, y0, x1, y1), color=(1, 1, 1), fill=True
                    )  # 흰색으로 덮기

                    page.insert_text(
                        (x0, y1),
                        random_ssn,
                        fontname="helv",
                        fontsize=12,
                        color=(0, 0, 0),
                    )

    # 변경된 PDF 저장
    pdf_document.save(output_pdf)
    pdf_document.close()


# 사용 예시
replace_ssn_in_pdf(r"D:\works\react-project\input.pdf", "output.pdf")
