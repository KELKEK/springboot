package com.shinhan.firstzone.repository3;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.PageRequestDTO;
import com.shinhan.firstzone.vo4.PageResultDTO;
import com.shinhan.firstzone.vo4.WebBoardDTO;
import com.shinhan.firstzone.vo4.WebBoardEntity;

@Service
public class WebBoardServiceImpl implements WebBoardService{

	@Autowired
	WebBoardRepository boardRepo;
	
	@Override
	public Long register(WebBoardDTO dto) {
		// TODO Auto-generated method stub
		WebBoardEntity newEntity = boardRepo.save(dtoToEntity(dto));
		return newEntity.getBno();
	}

	@Override
	public List<WebBoardDTO> getList() {
		List<WebBoardDTO> blist = boardRepo.findAll().stream().map(en->entityToDTO(en)).collect(Collectors.toList());
		//collect는 리스트를 만들기 위해 다시 묶어놓는 역할
		return blist;
	}
	
	public PageResultDTO<WebBoardDTO, WebBoardEntity> getList(PageRequestDTO pageRequestDTO){
		//quertdsl 제공 메서드 => findAll(predicate, pageable)
		Page<WebBoardEntity> result = boardRepo.findAll(makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()),
				pageRequestDTO.getPageable(Sort.by("bno").descending()));
		Function<WebBoardEntity, WebBoardDTO> fn = en->entityToDTO(en);
		PageResultDTO<WebBoardDTO, WebBoardEntity> result2 = new PageResultDTO<>(result, fn);
		return result2;
	}

	@Override
	public WebBoardDTO selectById(Long bno) {
//		Optional<WebBoardEntity> en = boardRepo.findById(bno);
//		if(en.isPresent()) {
//			return entityToDTO(en.get());//optional은 있을수도 없을수도 있으므로 있을경우 받아들인다는 .get을 붙여주어야 한다.
//		}else {
//			return null;
//		}
		
		WebBoardEntity en = boardRepo.findById(bno).orElse(null);
		if(en==null) return null;
		return entityToDTO(en);
	}

	@Override
	public void modify(WebBoardDTO dto) {
		boardRepo.findById(dto.getBno()).ifPresent(en->{
			en.setContent(dto.getContent());
			en.setTitle(dto.getTitle());
			MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
			en.setWriter(member);
			boardRepo.save(en);
		});
		
	}

	@Override
	public void delete(Long bno) {
		boardRepo.deleteById(bno);
	}
	
}
