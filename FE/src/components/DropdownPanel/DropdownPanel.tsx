import { styled } from "styled-components";
import { ReactNode, useEffect, useRef } from "react";

type Props = {
  title: string;
  top: string;
  left: string;
  children: ReactNode;
  closeDropdown(): void;
};

// type AssigneesData = {
//   assignees: AssigneesList[];
// };

// type LabelsData = {
//   labels: Label[];
// };

// type MilestonesData = {
//   milestones: DropdownMilestone[];
// };

// type AuthorsData = {
//   authors: AssigneesList[];
// };

export default function DropdownPanel({
  title,
  top,
  left,
  closeDropdown,
  children,
}: Props) {
  // const navigate = useNavigate();
  const dropdownRef = useRef<HTMLDivElement>(null);

  // useEffect(() => {
  //   if (type === "filter") {
  //     return;
  //   }
  //   const URL = `http://3.34.141.196/api/issues/${type}`;
  //   const fetchData = async () => {
  //     try {
  //       const response = await fetch(URL);
  //       if (!response.ok) {
  //         throw new Error("데이터를 가져오는 데 실패했습니다.");
  //       }
  //       const jsonData = await response.json();
  //       type === "assignees"
  //         ? setAssigneesData(jsonData)
  //         : type === "labels"
  //         ? setLabelsData(jsonData)
  //         : type === "milestones"
  //         ? setMilestonesData(jsonData)
  //         : setAuthorsData(jsonData);
  //     } catch (error) {
  //       console.log("error");
  //     }
  //   };

  //   fetchData();
  // }, []);

  useEffect(() => {
    const handleOutsideClick = (e: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(e.target as Node)
      ) {
        closeDropdown();
      }
    };

    document.addEventListener("mousedown", handleOutsideClick);

    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  }, [closeDropdown]);

  return (
    <Container ref={dropdownRef} $top={top} $left={left}>
      <Header>
        <Title>{title}</Title>
      </Header>
      {children}
    </Container>
  );
}

const Container = styled.div<{ $top: string; $left: string }>`
  position: absolute;
  z-index: 1000;
  top: ${({ $top }) => $top};
  left: ${({ $left }) => $left};
  width: 240px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  > div:last-child {
    border-bottom-left-radius: inherit;
    border-bottom-right-radius: inherit;
  }
`;

const Header = styled.div`
  padding: 8px 16px;
  border-top-left-radius: inherit;
  border-top-right-radius: inherit;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const Title = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;
