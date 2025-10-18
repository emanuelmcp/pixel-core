package io.github.emanuelmcp.pixelCore.domain;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInfo {
  String uuid;
  String nickname;
  BigInteger money;
}
